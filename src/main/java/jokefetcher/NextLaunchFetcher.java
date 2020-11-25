package jokefetcher;

import entities.NextLaunch;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;
import utils.HttpUtils;

public class NextLaunchFetcher {
        
        /*
        1 - REST ENDPOINT IS CALLED
        2 - FACADE CALLS THIS CLASS
        3 - THIS CLASS PERFORMS THE FETCH (DONE)
        4 - STORES THE FETCH IN DB (DONE)
        5 - RETURN FETCH TO REST ENDPOINT
        */
    public static void main(String[] args) throws IOException {
        getNextLaunchJson();
    }
    public static String getNextLaunchJson() throws IOException{

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();        
        
        String APIData = HttpUtils.fetchData("https://ll.thespacedevs.com/2.0.0/launch/upcoming?format=json");

        NextLaunch n1 = new NextLaunch(APIData);

        em.getTransaction().begin();
        //em.createNamedQuery("NextLaunch.deleteAllRows").executeUpdate();
        em.persist(n1);
        em.getTransaction().commit();        
        
        return APIData;
    }
}
