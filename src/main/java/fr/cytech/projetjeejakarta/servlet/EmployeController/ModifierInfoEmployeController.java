package fr.cytech.projetjeejakarta.servlet.EmployeController;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.enumeration.Grade;
import fr.cytech.projetjeejakarta.enumeration.Sexe;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/ModifierEmployeController")
public class ModifierInfoEmployeController extends HttpServlet {
    public  void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Employe employe = (Employe) session.getAttribute("employeFoundMatricule");

        String newNom = (String) session.getAttribute("newNom");
        String newPrenom = (String) session.getAttribute("newPrenom");
        String newAdresse = (String) session.getAttribute("newAdresse");
        String newNumero = (String) session.getAttribute("newNumero");
        String newEmail = (String) session.getAttribute("newEmail");
        Integer newIdDep = (Integer) session.getAttribute("newId_departement");
        Sexe newSexe = (Sexe) session.getAttribute("newSexe");
        Grade newGrade = (Grade) session.getAttribute("newGrade");
        int newSalaire =  (Integer) session.getAttribute("newSalaire");
        System.out.println("new salaire session : " + newSalaire);
        employe.setNom(newNom);
        employe.setPrenom(newPrenom);
        employe.setAdresse(newAdresse);
        employe.setNumero(newNumero);
        employe.setEmail(newEmail);
        employe.getDepartement().setId_departement(newIdDep);
        employe.setSexe(newSexe);
        employe.setGrade(newGrade);
        employe.setSalaire(newSalaire);


        EmployeDAO employeDAO = new EmployeDAO();
        employeDAO.updateEmploye(employe);

        System.out.println("Employe modifié");
        request.getRequestDispatcher("Employe/afficheEmploye.jsp").forward(request, response);
    }
}
