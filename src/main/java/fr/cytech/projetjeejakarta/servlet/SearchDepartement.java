package fr.cytech.projetmodel.servlet;


import fr.cytech.projetmodel.dao.DepartementDAO;
import fr.cytech.projetmodel.dao.EmployeDAO;
import fr.cytech.projetmodel.model.Departement;
import fr.cytech.projetmodel.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/departement")
public class SearchDepartement extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jdbcURL = "jdbc:mysql://localhost:3306/entreprise";
        String dbUser = "root";
        String dbPassword = "root";



        Departement departement;
        DepartementDAO dao = new DepartementDAO();
        departement = dao.fetchDepartement(1);

        HttpSession session = req.getSession();
        session.setAttribute("departementFound",departement);
        req.getRequestDispatcher("/modeltest.jsp").forward(req, resp);


        System.out.println("doPost Search   Departement"+ departement);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
