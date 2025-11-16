package fr.cytech.projetjeejakarta.util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory emf= buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try{
            return Persistence.createEntityManagerFactory("jeejakartaUtil");
        }
        catch(Throwable ex) {
            throw new ExceptionInInitializerError("Erreur lors de la création de EntityManagerFactory: " + ex);
        }
    }
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    public static void shutdown() {
        emf.close();
    }
}
