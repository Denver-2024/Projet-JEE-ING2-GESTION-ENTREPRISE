package fr.cytech.projetjeejakarta.servlet;
/*
import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.dao.RoleDAO;
import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/get-departemetEtRole")
public class FetchRolesAndDepartements extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        DepartementDAO departementDAO = new DepartementDAO();
        List<Departement> departementsFound = departementDAO.getAllDepartements();





        RoleDAO roleDAO = new RoleDAO();
        List<Role> rolesFound = roleDAO.getAllRoles();

        HttpSession session = request.getSession();

        session.getServletContext().setAttribute("departementsFound", departementsFound);
        System.out.println("departementsFound: " + departementsFound.size());

        session.getServletContext().setAttribute("rolesFound", rolesFound);
        request.getRequestDispatcher("/ajouterEmploye.jsp").forward(request, response);


    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
*/