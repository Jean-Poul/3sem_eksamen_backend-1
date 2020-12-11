package rest;

import dto.CommentDTO;
import entities.Comment;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

//@Disabled
public class CommentResourceTest {
    
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    
    private static Comment c1, c2;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        
        Comment c1 = new Comment("Comments!!", "222");
        Comment c2 = new Comment("More comments!!", "3333");

        
        try {
            
            em.getTransaction().begin();
            em.createNamedQuery("Comment.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();        
            
        } finally {
            
            em.close();
            
        }
        
    }
    
    @Test
    public void testServerIsUp() {
        
        System.out.println("Testing is server UP");
        
        given().when().get("/comments").then().statusCode(200);
    }
    
    @Test
    public void testCount() throws Exception {
        
        given()
                .contentType("application/json")
                .get("/comments/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2));

    }
    
    @Test
    public void testGetAllComments() {
        List<CommentDTO> commentsDTOs;

        commentsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/comments/all")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().jsonPath().getList("all", CommentDTO.class);

        CommentDTO c1DTO = new CommentDTO(c1);
        CommentDTO c2DTO = new CommentDTO(c2);
        
        assertThat(commentsDTOs, containsInAnyOrder(c1DTO, c2DTO));
        
        assertThat(commentsDTOs, hasSize(2));
    }

    @Test
    public void getUserComment() throws Exception {
        
        int expected = Math.toIntExact(c1.getId());
        
        //If you want to match on string use the code below and set body to "userComment"
        //String expected = c1.getComment(); 
        
        given()
                .contentType("application/json")
                .when()
                .get("/comments/" + c1.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .assertThat()
                .body("id", equalTo(expected));
    }

    @Test
    public void testAddComment() throws Exception {
        
        given()
                .contentType("application/json")
                .body(new CommentDTO(c1))
                .when()
                .post("comments")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("userComment", equalTo("c1 comment"))
                .body("id", notNullValue());
        
    }

    @Test
    public void deleteUserComment() throws Exception {
        given()
                .contentType("application/json")
                .body(new CommentDTO(c1))
                .when()
                .delete("comments/" + c1.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("userComment", equalTo("c1 comment"))
                .body("id", notNullValue());
    }
    
}

