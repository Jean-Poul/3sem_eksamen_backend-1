package facades;

import dto.CommentDTO;
import dto.CommentsDTO;
import entities.Comment;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

//@Disabled
public class CommentFacadeTest {
    
    private static EntityManagerFactory emf;
    private static CommentFacade facade;
    
    private Comment c1;
    private Comment c2;
    
    public CommentFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CommentFacade.getCommentFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }
    
    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        
        
        c1 = new Comment("c1 comment", "123");
        c2 = new Comment("c2 comment", "456");
        
        
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
    
    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }
    
    @Test
    public void testCommentCount() {
        
        assertEquals(2, facade.getCommentCount(), "Expects two rows in the database");     
    
    }
    
    @Test
    public void testGetAllComments() {
        
        CommentsDTO commentsDTO = facade.getAllComments();
        List<CommentDTO> list = commentsDTO.getAll();
        System.out.println("Liste af comments: "+list);
        assertThat(list, everyItem(Matchers.hasProperty("id")));
        assertThat(list, Matchers.hasItems(Matchers.<CommentDTO>hasProperty("userComment", is("c1 comment")),
                Matchers.<CommentDTO>hasProperty("userComment", is("c2 comment"))
                ));
        
    }
    
    @Test
    public void testGetUserComment() throws Exception {

        CommentDTO commentDTO = facade.getUserComment(c1.getId());
 
        assertEquals("c1 comment", commentDTO.getUserComment());

    }
    
    @Test
    public void testAddComment() throws Exception {
        
        CommentDTO commentDTO = facade.addComment("Comment fra test", "rocketID", "userName");
        assertEquals("Comment fra test", commentDTO.getUserComment());
        
    }
    
    
    @Test
    public void testdeleteComment() throws Exception {
        
       CommentDTO commentDTO = facade.deleteComment(c1.getId());
       assertThat(c1.getId(), is(not(commentDTO.getId())));
       assertEquals(1, facade.getCommentCount());
       
    }

    
}
