
package facades;

import dto.CommentDTO;
import dto.CommentsDTO;
import entities.Comment;
import errorhandling.CommentException;
import errorhandling.NoConnectionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class CommentFacade {
    private static EntityManagerFactory emf;
    private static CommentFacade instance;

    private CommentFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static CommentFacade getCommentFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CommentFacade();
        }
        return instance;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public long getCommentCount() throws NoConnectionException { 
        
        EntityManager em = emf.createEntityManager();
        
        try{
            long commentCount = (long)em.createQuery("SELECT COUNT(c) FROM Comment c").getSingleResult();
            return commentCount;
        }catch (Exception e){
            throw new NoConnectionException ("No connection to the database");
        }
        finally{  
            em.close();
        }
    }
    
        public CommentsDTO getAllComments() {
        EntityManager em = getEntityManager();
        try {
            return new CommentsDTO(em.createNamedQuery("Comment.getAllRows").getResultList());
        } finally {
            em.close();
        }
    }   
    
    public CommentDTO getUserComment(long id) throws CommentException {
        
        EntityManager em = getEntityManager();
        
        Comment comment = em.find(Comment.class, id);
        
        if (comment == null) {
            throw new CommentException("No user comment linked with provided id was found");
        } else {
            try {
                return new CommentDTO(comment);
            } finally {
                em.close();
            }
        }
    }
    
    public CommentDTO deleteComment(long id) throws CommentException {
        EntityManager em = getEntityManager();
        Comment comment = em.find(Comment.class, id);
        if (comment == null) {
            throw new CommentException("Could not delete, Id was not found");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(comment);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new CommentDTO(comment);
        }
    }
    
    public CommentDTO addComment(String addComment, String rocketID) throws CommentException {
        
        EntityManager em = emf.createEntityManager();
        Comment comment = new Comment(addComment, rocketID);
        
        if ((addComment.length() == 0 )) {
            throw new CommentException("Missing input");
        }
        
        try {
            em.getTransaction().begin();
            em.persist(comment);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
        return new CommentDTO(comment);

    }
    
}
