package fr.cytech.projetjeejakarta.servlet.EmployeController;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.enumeration.Grade;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/RechercheEmployeController")
public class RechercheEmployeController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String roleStr = request.getParameter("role");
        String gradeStr = request.getParameter("grade");
        String id_departementStr = request.getParameter("id_departement");



        if(roleStr == null && gradeStr == null && id_departementStr == null  && nom.isBlank() && prenom.isBlank()){
            request.setAttribute("errorMessageAucunParametre","Vous n'avez entré aucun paramètre de recherche");
            request.getRequestDispatcher("Employe/rechercheEmploye.jsp").forward(request, response);
            return;
        }

        Grade grade=null;
        if(gradeStr != null){
            try {
                grade = Grade.valueOf(gradeStr);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        }

        int id_role=0;
        if(roleStr != null){
            try{
                id_role = Integer.parseInt(roleStr);
            }catch(NumberFormatException e){
                e.printStackTrace();
            }

        }

        int id_departement=0;

        if(id_departementStr != null){
            try {
                id_departement = Integer.parseInt(id_departementStr);
            }catch(NumberFormatException e){
                e.printStackTrace();
            }

        }


        EmployeDAO employeDAO = new EmployeDAO();
        List<Employe> employes = employeDAO.rechercheEmployes(nom,prenom,id_departement,grade,id_role);

        if(employes.size()==0){
            request.setAttribute("messageAucunEmploye","Aucun employé trouvé avec vos critères de recherche");
            request.getRequestDispatcher("Employe/rechercheEmploye.jsp").forward(request, response);
            return;
        }
        request.setAttribute("employes",employes);
        request.getRequestDispatcher("Employe/rechercheEmploye.jsp").forward(request, response);
    }
}
