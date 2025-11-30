package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.dao.FicheDePaieDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.FicheDePaie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/RechercherLesFichesDePaieController")
public class RechercherLesFichesDePaieController extends HttpServlet {



    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");
        int id_employe = Integer.parseInt(req.getParameter("employe"));




        Date startDate =Date.valueOf(startDateStr) ;
        Date endDate =Date.valueOf(endDateStr) ;

        if(startDate.after(endDate)) {
            req.setAttribute("errorMessagePeriodeDate", "La date de la fin de l'absence ne peut pas être après la date du début de l'absence." );
            req.setAttribute("errorDatesPeriode","Vous avez saisie : \nDate début : "+startDate+"\n Date fin : "+endDate);
            req.getRequestDispatcher("Employe/rechercherFichesDePaie.jsp").forward(req, resp);
            return;
        }


        EmployeDAO employeDao = new  EmployeDAO();
        Employe employe = employeDao.fetchEmploye(id_employe);
        FicheDePaieDAO ficheDao = new FicheDePaieDAO();
        List<FicheDePaie> fiches = ficheDao.rechercheFicheDePaiePeriode(employe,startDate,endDate);


        // Sort by date descending (most recent first)
        fiches.sort((f1, f2) -> f2.getDateFiche().compareTo(f1.getDateFiche()));



        if(fiches.isEmpty()){
            req.setAttribute("messagePasDeFiche", "Aucune fiche de paie n'ai été trouvée." );

            req.getRequestDispatcher("Employe/rechercherFichesDePaie.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("fiches", fiches);

        req.getRequestDispatcher("Employe/rechercherFichesDePaie.jsp").forward(req, resp);

    }
}
