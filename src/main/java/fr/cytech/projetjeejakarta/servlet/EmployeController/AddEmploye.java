package fr.cytech.projetjeejakarta.servlet.EmployeController;

import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.dao.RoleDAO;
import fr.cytech.projetjeejakarta.enumeration.Grade;
import fr.cytech.projetjeejakarta.enumeration.Sexe;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/add-employe")
public class AddEmploye extends HttpServlet {
    public void  doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom =  request.getParameter("prenom");
        String adresse  = request.getParameter("adresse");
        String numero= request.getParameter("numero");
        String email  = request.getParameter("email");
        String id_departementString = request.getParameter("id_departement");
        Sexe sexe = null;
        String sexeParam = request.getParameter("sexe");
        Grade grade = null;
        String gradeParam = request.getParameter("grade");

        String roleIdStr = request.getParameter("role");



        if(nom == null || prenom == null || adresse == null || numero == null || email == null || sexeParam == null || gradeParam == null || id_departementString == null
                || roleIdStr.isEmpty() || nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || numero.isEmpty() || email.isEmpty() || sexeParam.isBlank() || gradeParam.isBlank() || id_departementString.isEmpty() || roleIdStr == null || roleIdStr.isBlank() ){
            request.setAttribute("errorMessageInputNotFilled","Vous devez remplir tous les champs");
            request.getRequestDispatcher("ajouterEmploye.jsp").forward(request, response);
            return;
        }

        try {
            sexe = Sexe.valueOf(sexeParam);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid sexe value: " + sexeParam);
        }
        try {
            grade = Grade.valueOf(gradeParam);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid grade value: " + gradeParam);
        }

        int roleId = Integer.parseInt(roleIdStr);
        RoleDAO roleDAO = new RoleDAO();
        Role role = roleDAO.rechercherRole(roleId);

        int id_departement = Integer.parseInt(id_departementString);

        System.out.println("Nom : "+nom+"\nPrenom : "+prenom+"\nAdresse : "+adresse+"\nNumero : "+numero+"\nEmail : "+email+"\nId departement : "+id_departement+"\nSexe : "+sexe+"\nGrade : "+grade+"\nRole ID : "+roleId);
        DepartementDAO departementDao=new DepartementDAO();
        Employe employe = new Employe(nom,prenom,adresse, departementDao.rechercherParId(id_departement),numero,email,sexe,grade,role);

        EmployeDAO employeDAO = new EmployeDAO();

        employeDAO.creerOuModifierEmploye(employe);

        request.setAttribute("messageAjoutSucces","L'employé a été ajouté aves succès");
        request.getRequestDispatcher("ajouterEmploye.jsp").forward(request, response);

    }
}
