/*package fr.cytech.projetjeejakarta.servlet;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.dao.RoleDAO;
import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Role;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@WebListener
public class FetchAllDepartementsAndRoles implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Create daemon threads for the scheduler
        ThreadFactory daemonThreadFactory = r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("FetchAllDepartmentsAndRolesScheduler");
            return t;
        };

        scheduler = Executors.newSingleThreadScheduledExecutor(daemonThreadFactory);

        Runnable refreshTask = () -> {
            try {
                DepartementDAO departementDAO = new DepartementDAO();
                List<Departement> departementsFound = departementDAO.getAllDepartements();
                sce.getServletContext().setAttribute("departementsFound", departementsFound);

                RoleDAO roleDAO = new RoleDAO();
                List<Role> rolesFound = roleDAO.getAllRoles();
                sce.getServletContext().setAttribute("rolesFound", rolesFound);

            } catch (Exception ignored) {

            }
        };

        // Schedule the task every 5 minutes, initial delay 0
        scheduler.scheduleAtFixedRate(refreshTask, 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //  Shutdown scheduler immediately
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow(); // cancel all tasks
            try {
                scheduler.awaitTermination(5, TimeUnit.SECONDS); // wait a bit for threads to exit
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }

        //  Close EntityManagerFactory silently
        try {
            JpaUtil.shutdown();
        } catch (Exception ignored) {
        }

        //  Cleanup MySQL abandoned connection threads silently
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception ignored) {
        }


    }
}
*/