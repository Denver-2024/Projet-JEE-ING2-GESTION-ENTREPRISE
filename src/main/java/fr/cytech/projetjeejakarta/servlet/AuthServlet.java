/*package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Role;
import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.dao.RoleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private EmployeDAO employeDAO;
    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        this.employeDAO = new EmployeDAO();
        this.roleDAO = new RoleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Afficher la page de connexion
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idEmployeStr = request.getParameter("id_employe");
        String password = request.getParameter("password");

        try {
            int idEmploye = Integer.parseInt(idEmployeStr);
            Employe employe = employeDAO.findById(idEmploye);

            if (employe != null && employe.getPassword() != null &&
                    employe.getPassword().equals(password)) {

                // Récupérer le rôle de l'employé
                Role role = roleDAO.findById(employe.getId_role());

                // Créer la session
                HttpSession session = request.getSession();
                session.setAttribute("employe", employe);
                session.setAttribute("role", role.getNom());
                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                // Rediriger vers la page d'accueil
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                request.setAttribute("error", "Identifiants incorrects");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Format d'identifiant incorrect");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'authentification");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}*/