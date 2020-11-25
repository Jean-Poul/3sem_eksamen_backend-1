package facades;

import dto.NextLaunchDTO;
import entities.NextLaunch;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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

    public NextLaunchDTO getNextLaunch() {
        EntityManager em = emf.createEntityManager();
        try {
            List jsonString = em.createQuery("SELECT d.data FROM NextLaunch d ORDER BY d.fetchTime DESC").getResultList();
            List jsonStringTime = em.createQuery("SELECT d.fetchTime FROM NextLaunch d ORDER BY d.fetchTime DESC").getResultList();
            System.out.println(jsonStringTime);
            System.out.println(jsonString.get(0));
            NextLaunchDTO nextLaunchDTO = new NextLaunchDTO(new NextLaunch(jsonString.get(0).toString()));
            return nextLaunchDTO;
        } finally {
            em.close();
        }
    }
}
