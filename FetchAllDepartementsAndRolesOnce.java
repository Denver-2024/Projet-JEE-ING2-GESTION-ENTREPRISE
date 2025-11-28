package fr.cytech.projetjeejakarta.servlet;

/*
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

@WebListener
public class FetchAllDepartementsAndRolesOnce implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Fetch departments once
            DepartementDAO departementDAO = new DepartementDAO();
            List<Departement> departementsFound = departementDAO.getAllDepartements();
            sce.getServletContext().setAttribute("departementsFound", departementsFound);

            // Fetch roles once
            RoleDAO roleDAO = new RoleDAO();
            List<Role> rolesFound = roleDAO.getAllRoles();
            sce.getServletContext().setAttribute("rolesFound", rolesFound);

        } catch (Exception ignored) {
            // ignore silently
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up resources at shutdown
        try {
            JpaUtil.shutdown();
        } catch (Exception ignored) {
        }
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception ignored) {
        }
    }
}
*/