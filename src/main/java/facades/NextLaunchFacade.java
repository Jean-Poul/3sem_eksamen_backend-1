package facades;

import dto.NextLaunchDTO;
import entities.NextLaunch;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jokefetcher.NextLaunchFetcher;

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

    public long getLaunchCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long launchCount = (long) em.createQuery("SELECT COUNT(u) FROM NextLaunch u").getSingleResult();
            return launchCount;
        } finally {
            em.close();
        }
    }

    public NextLaunchDTO getNextLaunch() throws IOException {
        EntityManager em = emf.createEntityManager();
        try {
            NextLaunchFetcher nextLaunchFetcher = new NextLaunchFetcher();
            String json = nextLaunchFetcher.getNextLaunchJson();
            NextLaunchDTO nlDTO = new NextLaunchDTO(new NextLaunch(json));
            return nlDTO;
        } finally {
            em.close();
        }
    }
}
