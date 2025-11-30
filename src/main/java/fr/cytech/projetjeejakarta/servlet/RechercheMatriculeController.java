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

@WebServlet("/RechercheMatriculeController")
public class RechercheMatriculeController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matriculeStr = request.getParameter("matricule");
        if(matriculeStr == null || matriculeStr.isEmpty() ){
            request.setAttribute("errorMessageMatriculeNotInt","Veuillez mettre un nombre comme matricule. Le matricule "+matriculeStr+" n'est pas valide");
            request.getRequestDispatcher("Employe/rechercheMatricule.jsp").forward(request, response);
            return;
        }

        int matricule;

        try {
            matricule = Integer.parseInt(matriculeStr);
        } catch (NumberFormatException e) {

            request.setAttribute("errorMessageMatriculeNotInt","Veuillez mettre un nombre comme matricule. Le matricule "+matriculeStr+" n'est pas valide");
            request.getRequestDispatcher("Employe/rechercheMatricule.jsp").forward(request, response);
            return;

        }

        if(matricule < 0 ){
            request.setAttribute("errorMessageMatriculeNotPositive","Veuillez mettre un nombre positive comme matricule. Le matricule "+matricule+" n'est pas valide");
            request.getRequestDispatcher("Employe/rechercheMatricule.jsp").forward(request, response);
            return;
        }

        Employe employeFoundMatricule = new Employe();
        EmployeDAO employeDAO = new EmployeDAO();
        employeFoundMatricule = employeDAO.fetchEmploye(matricule);

        if(employeFoundMatricule==null){
            request.setAttribute("errorMessageEmployeNotFound","L'employé avec le matricule "+matricule+" est introuvable");
            request.getRequestDispatcher("Employe/rechercheMatricule.jsp").forward(request, response);
            return;

        }
        else{
            HttpSession session = request.getSession();
            session.setAttribute("employeFoundMatricule", employeFoundMatricule);
            request.getRequestDispatcher("Employe/afficheEmploye.jsp").forward(request, response);

        }
    }
}
