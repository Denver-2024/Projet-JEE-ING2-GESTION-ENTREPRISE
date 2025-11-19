/*package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.*;
import fr.cytech.projetjeejakarta.enumeration.*;

import fr.cytech.projetjeejakarta.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/employe")
public class SearchEmploye extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jdbcURL = "jdbc:mysql://localhost:3306/entreprise";
        String dbUser = "root";
        String dbPassword = "cytech0001";



        Employe employe;
        EmployeDAO dao = new EmployeDAO();
        employe = dao.fetchEmploye(2);

        HttpSession session = req.getSession();
        session.setAttribute("employeFound",employe);
        req.getRequestDispatcher("/modeltest.jsp").forward(req, resp);


        System.out.println("doPost Search   Employe"+ employe);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
*/