package fr.cytech.projetmodel.servlet;

import fr.cytech.projetmodel.dao.EmployeDAO;
import fr.cytech.projetmodel.dao.RoleDAO;
import fr.cytech.projetmodel.model.Employe;
import fr.cytech.projetmodel.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/search-role")
public class SearchRole extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jdbcURL = "jdbc:mysql://localhost:3306/entreprise";
        String dbUser = "root";
        String dbPassword = "root";



        Role role;
        RoleDAO dao = new RoleDAO();
        role = dao.fetchRole(2);

        HttpSession session = req.getSession();
        session.setAttribute("roleFound",role);
        req.getRequestDispatcher("/modeltest.jsp").forward(req, resp);


        System.out.println("doPost Search   Role"+ role);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


}
