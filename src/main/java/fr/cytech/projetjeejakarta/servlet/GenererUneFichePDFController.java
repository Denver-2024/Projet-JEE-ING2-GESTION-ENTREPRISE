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

@WebServlet("/GenererUneFichePDFController")
public class GenererUneFichePDFController extends HttpServlet {


    private EmployeDAO employeDAO;


    @Override
    public void init() throws ServletException {

        employeDAO = new EmployeDAO();

    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id_employe =  Integer.parseInt(request.getParameter("id_employe"));
        int id_fiche_de_paie =  Integer.parseInt(request.getParameter("id_fiche_de_paie"));
        String logoPath = getServletContext().getRealPath("resources/img/logoClient.png");
        String signaturePath = getServletContext().getRealPath("resources/img/signature.png");
        FicheDePaieDAO ficheDao = new FicheDePaieDAO();
        FicheDePaie fiche = ficheDao.rechercherFicheDePaie(id_fiche_de_paie);


        Employe employe = employeDAO.rechercherParId(id_employe);

        byte[] pdfBytes=ficheDao.generateFicheDePaie(logoPath,signaturePath,employe,fiche);
        if (pdfBytes == null) {
            response.sendError(500, "PDF generation failed");
            return;
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=fiche_de_paie.pdf");
        response.setContentLength(pdfBytes.length);

        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }
}
