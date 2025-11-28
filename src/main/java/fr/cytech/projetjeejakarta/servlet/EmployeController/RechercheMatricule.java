package fr.cytech.projetjeejakarta.servlet.EmployeController;


import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/recherche-matricule")
public class RechercheMatricule extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matriculeStr = request.getParameter("matricule");
        if(matriculeStr == null || matriculeStr.isEmpty() ){
            request.setAttribute("errorMessageMatriculeNotInt","Veuillez mettre un nombre comme matricule. Le matricule "+matriculeStr+" n'est pas valide");
            request.getRequestDispatcher("rechercheMatricule.jsp").forward(request, response);
            return;
        }

        int matricule;

        try {
            matricule = Integer.parseInt(matriculeStr);
        } catch (NumberFormatException e) {

            System.out.println("Invalid matricule: " + matriculeStr);
            request.setAttribute("errorMessageMatriculeNotInt","Veuillez mettre un nombre comme matricule. Le matricule "+matriculeStr+" n'est pas valide");
            request.getRequestDispatcher("rechercheMatricule.jsp").forward(request, response);
            return;

        }

        if(matricule < 0 ){
            request.setAttribute("errorMessageMatriculeNotPositive","Veuillez mettre un nombre positive comme matricule. Le matricule "+matricule+" n'est pas valide");
            request.getRequestDispatcher("rechercheMatricule.jsp").forward(request, response);
            return;
        }

        Employe employeFoundMatricule = new Employe();
        EmployeDAO employeDAO = new EmployeDAO();
        employeFoundMatricule = employeDAO.rechercherParId(matricule);

        if(employeFoundMatricule==null){
            request.setAttribute("errorMessageEmployeNotFound","L'employé avec le matricule "+matricule+" est introuvable");
            request.getRequestDispatcher("rechercheMatricule.jsp").forward(request, response);
        }
        else{
            HttpSession session = request.getSession();
            session.setAttribute("employeFoundMatricule", employeFoundMatricule);
            request.getRequestDispatcher("/afficheEmploye.jsp").forward(request, response);

        }
    }
}
