package fr.cytech.projetjeejakarta.util;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("### Initializing EntityManagerFactory ###");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jeejakartaUtil");
        sce.getServletContext().setAttribute("emf", emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("emf");

        // Stop Hibernate connection provider threads BEFORE closing EMF
        if (emf != null) {
            try {
                DriverManagerConnectionProviderImpl provider =
                        emf.unwrap(DriverManagerConnectionProviderImpl.class);
                if (provider != null) {
                    provider.stop();
                }
            } catch (Exception ignored) {}
        }

        // Close EntityManagerFactory
        if (emf != null && emf.isOpen()) {
            emf.close();
        }

        // Shutdown MySQL cleanup threads
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Deregister JDBC drivers
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == getClass().getClassLoader()) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (Exception ignored) {}
            }
        }
    }
}