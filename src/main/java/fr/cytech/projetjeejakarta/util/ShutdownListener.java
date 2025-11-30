package fr.cytech.projetjeejakarta.util;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ShutdownListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.shutdown();
        AbandonedConnectionCleanupThread.checkedShutdown();
        System.out.println("EntityManagerFactory and MySQL cleanup done.");
    }
}