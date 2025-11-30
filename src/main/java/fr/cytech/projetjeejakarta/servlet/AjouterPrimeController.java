package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.FicheDePaieDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Month;

@WebServlet("/AjouterPrimeController")
public class AjouterPrimeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String primeStr=request.getParameter("prime");
        Month month = Month.valueOf(request.getParameter("mois"));

        int prime;
        try {
            prime=Integer.parseInt(primeStr);
        }catch (NumberFormatException e){
            request.setAttribute("errorPrime","La prime doit être un nombre entier positive");
            request.getRequestDispatcher("Employe/ajouterPrime.jsp").forward(request, response);
            return;
        }
        if(prime < 0){
            request.setAttribute("errorPrime","La prime doit être un nombre entier positive");
            request.getRequestDispatcher("Employe/ajouterPrime.jsp").forward(request, response);
            return;
        }

        if(prime > 100000){
            request.setAttribute("errorPrimeTrop","La prime est trop élevée");
            request.getRequestDispatcher("Employe/ajouterPrime.jsp").forward(request, response);
            return;
        }
        Employe employe = (Employe) request.getSession().getAttribute("employeFoundMatricule");
        if (employe == null) {
            request.setAttribute("error", "Aucun employé sélectionné.");
            request.getRequestDispatcher("Employe/ajouterPrime.jsp").forward(request, response);
            return;
        }
        FicheDePaieDAO dao = new FicheDePaieDAO();
        dao.saveOrUpdatePrime(employe, month, prime);

        request.setAttribute("successPrime", "Prime ajoutée avec succès !");
        request.getRequestDispatcher("Employe/ajouterPrime.jsp").forward(request, response);

        System.out.println("Prime : "+primeStr);
        System.out.println("Month : "+month);
    }
}
