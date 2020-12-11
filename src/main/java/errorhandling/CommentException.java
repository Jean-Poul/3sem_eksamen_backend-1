package errorhandling;

/**
 *
 * @author lam@cphbusiness.dk
 */
public class CommentException extends Exception {

    public CommentException(String message) {
        super(message);
    }

    public CommentException() {
        super("No Connection to the server");
    }
}
