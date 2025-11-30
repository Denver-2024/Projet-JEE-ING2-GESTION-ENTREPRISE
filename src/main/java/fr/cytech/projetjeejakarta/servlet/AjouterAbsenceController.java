package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.AbsenceDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/AjouterAbsenceController")
public class AjouterAbsenceController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));

        if(startDate.after(endDate)) {
            request.setAttribute("errorMessageAbsenceDate", "La date de la fin de l'absence ne peut pas être après la date du début de l'absence." );
            request.setAttribute("errorDatesAbsence","Vous avez saisie : \nDate début : "+startDate+"\n Date fin : "+endDate);
            request.getRequestDispatcher("Employe/ajouterAbsence.jsp").forward(request, response);
            return;
        }

        Employe employe = (Employe) request.getSession().getAttribute("employeFoundMatricule");

        AbsenceDAO absenceDAO = new AbsenceDAO();
        try {
            absenceDAO.logAbsenceExcludeWeekends(employe,startDate,endDate);
            request.setAttribute("messageAbsenceAjoutee", "L'absence a été ajoutée." );

            request.getRequestDispatcher("Employe/ajouterAbsence.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
