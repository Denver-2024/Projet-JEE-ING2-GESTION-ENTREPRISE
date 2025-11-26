package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.RoleDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Set;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        this.roleDAO = new RoleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("employe") != null) {
            Employe employe = (Employe) session.getAttribute("employe");

            Role role = employe.getRole();

            if (role != null) {
                Role roleWithAutorisations = roleDAO.findWithAutorisations(role);
                session.setAttribute("role", roleWithAutorisations.getNom());
                session.setAttribute("autorisations", roleWithAutorisations.getAutorisations());
            }

            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/auth");
        }
    }
}