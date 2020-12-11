package nextLaunchFetcher;

import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NextLaunchFetcherTest {
    
    public NextLaunchFetcherTest() {
    }
    
    private static EntityManagerFactory emf;
    private static NextLaunchFetcher instance;

    public static NextLaunchFetcher getNextLaunchFetcher(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new NextLaunchFetcher();
        }
        return instance;
    }

    @Test
    public void testGetNextLaunchJson() throws Exception {
            instance = getNextLaunchFetcher(emf);
            String dataString = instance.getNextLaunchJson();
            assertTrue(dataString.length() > 100);
    }
}
