package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.util.PasswordUtil;
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
            System.out.println("Password saisi: " + password);
            System.out.println("Hash en base: " + employe.getPassword());
            System.out.println("Check BCrypt: " + PasswordUtil.checkPassword(password, employe.getPassword()));

            if (employe != null) {
                boolean passwordMatch = employe.getPassword() != null && PasswordUtil.checkPassword(password, employe.getPassword());
            }

            if (employe != null && employe.getPassword() != null &&
                    PasswordUtil.checkPassword(password, employe.getPassword())) {
                Role role = employe.getRole();

                HttpSession session = request.getSession();
                session.setAttribute("employe", employe);

                if (role != null) {
                    session.setAttribute("role", role.getNom());
                } else {
                    session.setAttribute("role", "Non défini");
                }

                session.setMaxInactiveInterval(30 * 60);

                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                request.setAttribute("error", "Identifiants incorrects");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'authentification");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}