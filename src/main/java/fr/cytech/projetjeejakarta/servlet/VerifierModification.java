/*package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.enumeration.Grade;
import fr.cytech.projetjeejakarta.enumeration.Sexe;
import fr.cytech.projetjeejakarta.model.Departement;
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

@WebServlet("/verification-modification-employe")
public class VerifierModification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom =  request.getParameter("prenom");
        String adresse  = request.getParameter("adresse");
        String numero= request.getParameter("numero");
        String email  = request.getParameter("email");
        String id_departementString = request.getParameter("id_departement");
        int id_departement = Integer.parseInt(id_departementString);
        Sexe sexe = null;
        String sexeParam = request.getParameter("sexe");
        Grade grade = null;
        String gradeParam = request.getParameter("grade");

        if(nom == null || prenom == null || adresse == null || numero == null || email == null || sexeParam == null || gradeParam == null || id_departementString == null
                || nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || numero.isEmpty() || email.isEmpty() || sexeParam.isBlank() || gradeParam.isBlank() || id_departementString.isEmpty()){
            request.setAttribute("errorMessageModifInputNotFilled","Vous devez remplir tous les champs");
            request.getRequestDispatcher("modifierEmploye.jsp").forward(request, response);
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

        HttpSession session = request.getSession(false); // false avoids creating a new session
        if (session != null) {
            Object obj = session.getAttribute("employeFoundMatricule");
            if (obj != null) {
                // Cast it to the correct type
                Employe employeAModifier = (Employe) obj;
                List<String > changes = new ArrayList<>();

                if(!nom.equals(employeAModifier.getNom())){
                    changes.add("Le nom de l'employé sera changé de " + employeAModifier.getNom() + " à " + nom);
                }

                if(!prenom.equals(employeAModifier.getPrenom())){
                    changes.add("Le nom de l'employé sera changé de " + employeAModifier.getPrenom() + " à " + prenom);
                }

                if(!adresse.equals(employeAModifier.getAdresse())){
                    changes.add("L'adresse  de l'employé sera changée de " + employeAModifier.getAdresse() + " à " + adresse);
                }

                if(!numero.equals(employeAModifier.getNumero())){
                    changes.add("Le numéro de téléphone de l'employé sera changé de " + employeAModifier.getNumero() + " à " + numero);
                }

                if(!email.equals(employeAModifier.getEmail())){
                    changes.add("L'adrsse mail  de l'employé sera changée de " + employeAModifier.getEmail() + " à " + email);
                }

                if(!(id_departement == employeAModifier.getId_departement())){
                    Departement oldDepartement = new Departement();
                    Departement newDepartement = new Departement();
                    DepartementDAO dao = new DepartementDAO();
                    newDepartement =dao.fetchDepartement(id_departement);
                    oldDepartement=dao.fetchDepartement(employeAModifier.getId_departement());
                    changes.add("Le département de l'employé sera changée de " + oldDepartement.getNom() + " à " + newDepartement.getNom());
                }

                if(!sexe.equals(employeAModifier.getSexe())){
                    changes.add("Le sexe de l'employé sera changé de " + employeAModifier.getSexe() + " à " + sexe);
                }

                if(!grade.equals(employeAModifier.getGrade())){
                    changes.add("Le grade  de l'employé sera changé de " + employeAModifier.getGrade() + " à " + grade);
                }

                if(changes.isEmpty()){

                    request.setAttribute("messagePasDeModification","Vous n'avez effectuez aucun changement");
                    request.getRequestDispatcher("modifierEmploye.jsp").forward(request, response);
                    return;

                }
                else{
                    session.setAttribute("newNom", nom);
                    session.setAttribute("newPrenom", prenom);
                    session.setAttribute("newAdresse", adresse);
                    session.setAttribute("newNumero", numero);
                    session.setAttribute("newEmail", email);
                    session.setAttribute("newId_departement", id_departement);
                    session.setAttribute("newSexe", sexe);
                    session.setAttribute("newGrade", grade);


                    request.setAttribute("changes", changes);
                    request.getRequestDispatcher("/verificationDesChangements.jsp").forward(request, response);

                }

                System.out.println("Employeé à vérifier les modification : "+employeAModifier);
                System.out.println(changes);

            }
        }


    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
*/