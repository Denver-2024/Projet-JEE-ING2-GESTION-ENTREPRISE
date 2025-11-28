package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.dao.RoleDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/changer-role")
public class ChangerRole extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String roleIdStr = request.getParameter("role");

        int roleId = Integer.parseInt(roleIdStr);
        RoleDAO roleDAO = new RoleDAO();
        Role role = roleDAO.fetchRole(roleId);



        HttpSession session = request.getSession(false);

        Employe employe = (Employe) session.getAttribute("employeFoundMatricule");
        if(role.getId_role() == employe.getRole().getId_role()) {
            request.setAttribute("messagePasDeModificationRole","Vous n'avez effectuez aucun changement");
            request.getRequestDispatcher("changerLeRole.jsp").forward(request, response);
            return;
        }

        employe.setRole(role);
        EmployeDAO employeDAO = new EmployeDAO();
        employeDAO.updateEmploye(employe);

        request.setAttribute("messageRoleChanged","Le rôle de employé a été changé ");
        request.getRequestDispatcher("changerLeRole.jsp").forward(request, response);
        return;



    }
}
