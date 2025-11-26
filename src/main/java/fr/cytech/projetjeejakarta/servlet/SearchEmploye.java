package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.model.Employe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/search-employe")
public class SearchEmploye extends HttpServlet {

    private final EmployeDAO employeDAO = new EmployeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/Employe/rechercherEmploye.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String nomParam = request.getParameter("nom");
        String deptIdParam = request.getParameter("departementId");
        String roleIdParam = request.getParameter("role");

        // Blah blah on normalise...
        Integer id = (idParam != null && !idParam.trim().isEmpty()) ? Integer.valueOf(idParam) : null;
        String nom = (nomParam != null && !nomParam.trim().isEmpty()) ? nomParam.trim() : null;
        Integer deptId = (deptIdParam != null && !deptIdParam.trim().isEmpty()) ? Integer.valueOf(deptIdParam) : null;

        List<Employe> resultats = new ArrayList<>();
        resultats = employeDAO.afficherTous();

        if (id != null) {
            Employe e = employeDAO.rechercherParId(id);
            List<Employe> resultatsRecherche = new ArrayList<>();
            if (e != null)
                resultatsRecherche = new ArrayList<>();
            else
                resultatsRecherche = new ArrayList<>(Collections.singleton(e));
            resultats = filtrerEmployes(resultats, resultatsRecherche);
        }

        if (nom != null) {
            List<Employe> resultatsRecherche = employeDAO.rechercherParNom(nom);
            resultats = filtrerEmployes(resultats, resultatsRecherche);
        }

        if (deptId != null) {
            List<Employe> resultatsRecherche = employeDAO.rechercherParDepartement(deptId);
            resultats = filtrerEmployes(resultats, resultatsRecherche);
        }

        request.setAttribute("resultats", resultats);
        request.getRequestDispatcher("/Employe/rechercherEmploye.jsp").forward(request, response);
    }

    private List<Employe> filtrerEmployes(List<Employe> liste, List<Employe> filtre) {
        if (liste.isEmpty()) {
            return Collections.emptyList();
        }

        if (filtre == null || filtre.isEmpty()) {
            return Collections.emptyList();
        }

        return liste.stream()
                .filter(e -> {
                    for (Employe f: filtre) {
                        if (f.getId_employe() == e.getId_employe()) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}