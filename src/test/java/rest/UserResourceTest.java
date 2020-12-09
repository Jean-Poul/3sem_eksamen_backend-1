/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dto.UserDTO;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

//@Disabled
public class UserResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    //private static FacadeExample facade;
    private User u1, u2, u3;
    private Role r1, r2;

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
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            r1 = new Role("user");
            r2 = new Role("admin");
            u1 = new User("testmand1", "storFedAgurk");
            u2 = new User("testmand2", "lilleFedTomat");

            u3 = new User("Ulla", "Muffe");

            u1.addRole(r1);
            u2.addRole(r2);

            em.persist(r1);
            em.persist(r2);
            em.persist(u1);
            em.persist(u2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //@Disabled
    @Test
    public void testServerIsUp() {

        System.out.println("Testing is server UP");

        given().when().get("/users").then().statusCode(200);
    }

    //@Disabled
    @Test
    public void testCount() throws Exception {

        given()
                .contentType("application/json")
                .get("/users/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(equalTo("["+2+"]"));

    }

    @Disabled
    @Test
    public void testGetRole() throws Exception {

        String expected = "{"+ "code" + ":" + "403,\n" + "message:" + "Not authenticated - do login" +"}";
        
        System.out.println(u1.getRolesAsStrings());

        //If you want to match on string use the code below and set body to "userComment"
        //String expected = c1.getComment(); 
        given()
                .contentType("application/json")
                .when()
                .get("users/" + "user")
                .then()
                .statusCode(403)
                .assertThat()
                .body(equalTo(expected));
    }

    //@Disabled
    @Test
    public void testAddUser() throws Exception {
        given()
                .contentType("application/json")
                .body(new UserDTO(u3))
                .when()
                .post("/users/")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("userID", equalTo("Ulla"));

    }

}
