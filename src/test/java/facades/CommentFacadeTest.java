
package facades;

import dto.CommentDTO;
import entities.Comment;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;


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
        
        c1 = new Comment("c1 comment");
        c2 = new Comment("c2 comment");
        
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
    public void testGetCommentById() throws Exception {

        CommentDTO commentDTO = facade.getUserComment(c1.getId());
        assertEquals("c1 comment", commentDTO.getUserComment());

    }
    
    @Test
    public void testAddComment() throws Exception {
        
        facade.addComment("Jeg poster comments for at teste");
        assertEquals(3, facade.getCommentCount());
        
    }

    
}
