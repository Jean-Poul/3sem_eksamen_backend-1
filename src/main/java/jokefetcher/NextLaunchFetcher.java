package jokefetcher;

import entities.NextLaunch;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;
import utils.HttpUtils;

public class NextLaunchFetcher {

    int timeIntervalMinutes = 61; //Max time between new fetches
    List jsonString;
    String newJson;

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    public String getNextLaunchJson() throws IOException {

        //Gets the current time
        LocalDateTime currentTime = LocalDateTime.now();

        //Gets the time for the latest DB entry
        List jsonStringTime = em.createQuery("SELECT d.fetchTime FROM NextLaunch d ORDER BY d.fetchTime DESC").getResultList();
        LocalDateTime dbTimeStamp = (LocalDateTime) jsonStringTime.get(0);

        //Compares the difference in minutes
        long timeDifference = ChronoUnit.MINUTES.between(dbTimeStamp, currentTime);

        //If defined time has passed, perform new fetch and store in DB
        //If not, use the latest DB entry
        if (timeDifference > timeIntervalMinutes) {
            try {
                System.out.println("\n### Trying to fetch new data");
                String APIData = HttpUtils.fetchData("https://ll.thespacedevs.com/2.0.0/launch/upcoming?format=json");
                NextLaunch n1 = new NextLaunch(APIData);
                em.getTransaction().begin();
                em.createNamedQuery("NextLaunch.deleteAllRows").executeUpdate();
                em.persist(n1);
                jsonString = em.createQuery("SELECT d.data FROM NextLaunch d ORDER BY d.fetchTime DESC").getResultList();
                newJson = (String) jsonString.get(0);
                em.getTransaction().commit();
                System.out.println("\n### Fetched new data! - comming up!");
                return newJson;
            } finally {
                em.close();
            }
        } else {
            try {
                System.out.println("\n### Data was fetched less than " + timeIntervalMinutes + " minutes ago - Serving latest entry from database");
                em.getTransaction().begin();
                jsonString = em.createQuery("SELECT d.data FROM NextLaunch d ORDER BY d.fetchTime DESC").getResultList();
                newJson = (String) jsonString.get(0);
                return newJson;
            } finally {
                em.close();
            }
        }
    }
}

