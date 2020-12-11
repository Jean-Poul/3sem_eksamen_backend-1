package facades;

import dto.NextLaunchDTO;
import entities.NextLaunch;
import errorhandling.NoConnectionException;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import nextLaunchFetcher.NextLaunchFetcher;

public class NextLaunchFacade {

    private static EntityManagerFactory emf;
    private static NextLaunchFacade instance;

    public NextLaunchFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static NextLaunchFacade getNextLaunchFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new NextLaunchFacade();
        }
        return instance;
    }

    public long getLaunchCount() throws NoConnectionException { 
        EntityManager em = emf.createEntityManager();
        try {
            long launchCount = (long) em.createQuery("SELECT COUNT(u) FROM NextLaunch u").getSingleResult();
            return launchCount;
            }catch (Exception e){
            throw new NoConnectionException ("No connection to the database");
        } finally {
            em.close();
        }
    }

    public NextLaunchDTO getNextLaunch() throws IOException, NoConnectionException {
        EntityManager em = emf.createEntityManager();
        try {
            NextLaunchFetcher nextLaunchFetcher = new NextLaunchFetcher();
            String json = nextLaunchFetcher.getNextLaunchJson();
            NextLaunchDTO nlDTO = new NextLaunchDTO(new NextLaunch(json));
            return nlDTO;
        }catch (Exception e) {
            throw new NoConnectionException ("No connection to the api");
        } finally {
            em.close();
        }
    }
}
