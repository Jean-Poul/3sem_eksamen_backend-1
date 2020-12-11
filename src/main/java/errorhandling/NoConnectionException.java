package errorhandling;

/**
 *
 * @author lam@cphbusiness.dk
 */
public class NoConnectionException extends Exception {

    public NoConnectionException(String message) {
        super(message);
    }

    public NoConnectionException() {
        super("No Connection to the server");
    }
}
