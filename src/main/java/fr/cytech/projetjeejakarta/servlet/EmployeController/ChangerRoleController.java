package fr.cytech.projetjeejakarta.servlet.EmployeController;

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

@WebServlet("/ChangeRoleController")

public class ChangerRoleController extends HttpServlet {

    private RoleDAO roleDAO;
    private EmployeDAO employeDAO;

    @Override
    public void init() throws ServletException {

        roleDAO=new RoleDAO();
        employeDAO=new EmployeDAO();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String roleIdStr = request.getParameter("role");

        int roleId = Integer.parseInt(roleIdStr);
        Role role = roleDAO.rechercherRole(roleId);

        HttpSession session = request.getSession(false);

        Employe employe = (Employe) session.getAttribute("employeFoundMatricule");
        if(role.getId_role().equals(employe.getRole().getId_role())) {
            request.setAttribute("messagePasDeModificationRole","Vous n'avez effectué aucun changement");
            request.getRequestDispatcher("Employe/changerLeRole.jsp").forward(request, response);
            return;
        }


        // If new role = chef_de_departement → special logic
        if (role.getId_role() == 3) {
            employeDAO.promoteToChefDeDepartement(employe);
            request.setAttribute("messageRoleChanged",
                    "Le rôle a été changé. Ce salarié est maintenant Chef de département.");
        }
        // Normal role change
        else {
            employe.setRole(role);
            employeDAO.updateEmploye(employe);

            request.setAttribute("messageRoleChanged",
                    "Le rôle de l'employé a été changé.");
        }

        request.getRequestDispatcher("Employe/changerLeRole.jsp").forward(request, response);

    }
}
