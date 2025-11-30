package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.model.Autorisation;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/autorisationsController")
public class AutorisationsController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Liste Autorisations débutée");
        Employe employeConnecte = null;
        HttpSession session = request.getSession(false); // false avoids creating a new session
        if (session != null) {
            Object obj = session.getAttribute("employe");
            if (obj != null) {
                employeConnecte = (Employe) obj;
            }
        }
        List<Autorisation> listeAutorisation = new ArrayList<Autorisation>();
        EmployeDAO dao = new EmployeDAO();
        listeAutorisation = dao.getAutorisationsByEmployeId(employeConnecte.getId_employe());
        System.out.println("Liste Autorisations :" + listeAutorisation);

        if (listeAutorisation.isEmpty()) {
            System.out.println("Liste Autorisations empty");
        }



    }
}
