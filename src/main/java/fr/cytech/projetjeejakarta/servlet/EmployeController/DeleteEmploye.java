package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/supprimer-employe")
public class DeleteEmploye extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Employe employe = (Employe) session.getAttribute("employeFoundMatricule");
        EmployeDAO employeDAO = new EmployeDAO();
        employeDAO.supprimerEmploye(employe.getId_employe());
        session.removeAttribute("employeFoundMatricule");

        request.setAttribute("messageEmployeDeleted","L'employe a été supprimé");
        request.getRequestDispatcher("verificationDeLaSuppression.jsp").forward(request, response);
    }
}
