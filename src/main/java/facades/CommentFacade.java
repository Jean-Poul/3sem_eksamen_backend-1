package facades;

import dto.CommentDTO;
import dto.CommentsDTO;
import dto.UserDTO;
import entities.Comment;
import entities.User;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

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

    public long getCommentCount() {

        EntityManager em = emf.createEntityManager();

        try {
            long commentCount = (long) em.createQuery("SELECT COUNT(c) FROM Comment c").getSingleResult();
            return commentCount;
        } finally {
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

    public CommentDTO getUserComment(long id) throws Exception {

        EntityManager em = getEntityManager();

        Comment comment = em.find(Comment.class, id);

        if (comment == null) {
            throw new Exception("No user comment linked with provided id was found");
        } else {
            try {
                return new CommentDTO(comment);
            } finally {
                em.close();
            }
        }
    }

    public CommentDTO deleteComment(long id) throws Exception {
        EntityManager em = getEntityManager();
        Comment comment = em.find(Comment.class, id);
        if (comment == null) {
            throw new Exception("Could not delete, Id was not found");
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

    public CommentDTO addComment(String addComment, String rocketID, String userName) throws NotFoundException {

        EntityManager em = emf.createEntityManager();

        Comment comment = new Comment(addComment, rocketID);

        User u = em.find(User.class, userName);

        if (u == null) {
            throw new NotFoundException();
        }

        System.out.println("USERNAME: " + u.getUserName());

        u.addComment(comment);

        System.out.println(comment.toString());

        if ((addComment.length() == 0)) {
            throw new NotFoundException("No comment was found");
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

    public static void main(String[] args) throws Exception {

        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

        CommentFacade cf = getCommentFacade(EMF);

        cf.addComment("test fra facade", "123", "Per");

    }

}
