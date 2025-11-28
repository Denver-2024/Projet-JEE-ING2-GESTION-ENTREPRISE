package fr.cytech.projetjeejakarta.servlet;

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
        System.out.println("Nuh uh");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom =  request.getParameter("prenom");
        String salaireStr = request.getParameter("salaire");
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
        int salaire;
        try{
            salaire = Integer.parseInt(salaireStr);
            if(salaire < 500){
                request.setAttribute("errorSalaireTooLow","Le salaire minimum est de 500 €");
                request.getRequestDispatcher("ajouterEmploye.jsp").forward(request, response);
                return;
            }


        }catch (NumberFormatException e){
            request.setAttribute("errorSalaireNotNumber","Vous devez remplir un nombre entier comme salaire ( positif et inférieur à 2147483647 )");
            request.getRequestDispatcher("ajouterEmploye.jsp").forward(request, response);
            return;
        }



        int roleId = Integer.parseInt(roleIdStr);
        RoleDAO roleDAO = new RoleDAO();
        Role role = roleDAO.fetchRole(roleId);

        int id_departement = Integer.parseInt(id_departementString);

        System.out.println("Nom : "+nom+"\nPrenom : "+prenom+"\nAdresse : "+adresse+"\nNumero : "+numero+"\nEmail : "+email+"\nId departement : "+id_departement+"\nSexe : "+sexe+"\nGrade : "+grade+"\nRole ID : "+roleId);

        Employe employe = new Employe(nom,prenom,salaire,adresse,id_departement,numero,email,sexe,grade,role);

        EmployeDAO employeDAO = new EmployeDAO();

        employeDAO.saveEmploye(employe);

        request.setAttribute("messageAjoutSucces","L'employé a été ajouté aves succès");
        request.getRequestDispatcher("ajouterEmploye.jsp").forward(request, response);


    }
}
