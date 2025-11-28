/*package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.dao.RoleDAO;
import fr.cytech.projetjeejakarta.enumeration.Grade;
import fr.cytech.projetjeejakarta.enumeration.Sexe;
import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/EmployeController")
public class EmployeController extends HttpServlet {

    private EmployeDAO employeDAO;
    private RoleDAO roleDAO;
    private DepartementDAO departementDAO;

    @Override
    public void init() throws ServletException {
        employeDAO = new EmployeDAO();
        roleDAO = new RoleDAO();
        departementDAO = new DepartementDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "ajouter":
                ajouterEmploye(request, response);
                break;
            case "supprimer":
                supprimerEmploye(request, response);
                break;
            case "modifier":
                modifierEmploye(request, response);
                break;
            case "changerRole":
                changerRole(request, response);
                break;
            case "rechercher":
                rechercherEmploye(request, response);
                break;
            case "verifierModification":
                verifierModification(request, response);
                break;
            default:
                request.setAttribute("messageErreur", "Action inconnue");
                request.getRequestDispatcher("Employe/rechercheMatricule.jsp").forward(request, response);
        }
    }

    // --- AJOUT ---
    private void ajouterEmploye(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String numero = request.getParameter("numero");
        String email = request.getParameter("email");
        String sexeParam = request.getParameter("sexe");
        String gradeParam = request.getParameter("grade");
        String roleIdStr = request.getParameter("role");
        String idDepStr = request.getParameter("id_departement");

        if (nom == null || prenom == null || adresse == null || numero == null || email == null ||
                sexeParam == null || gradeParam == null || roleIdStr == null || idDepStr == null) {
            request.setAttribute("errorMessageInputNotFilled", "Vous devez remplir tous les champs");
            request.getRequestDispatcher("Employe/ajouterEmploye.jsp").forward(request, response);
            return;
        }

        Sexe sexe = Sexe.valueOf(sexeParam);
        Grade grade = Grade.valueOf(gradeParam);
        Role role = roleDAO.rechercherRole(Integer.parseInt(roleIdStr));
        Departement departement = departementDAO.rechercherParId(Integer.parseInt(idDepStr));

        Employe employe = new Employe(nom, prenom, adresse, departement, numero, email, sexe, grade, role);
        employeDAO.creerOuModifierEmploye(employe);

        request.setAttribute("messageAjoutSucces", "L'employé a été ajouté avec succès");
        request.getRequestDispatcher("Employe/ajouterEmploye.jsp").forward(request, response);
    }

    // --- SUPPRESSION ---
    private void supprimerEmploye(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        employeDAO.supprimerEmploye(id);
        request.setAttribute("messageEmployeDeleted", "L'employé a été supprimé");
        request.getRequestDispatcher("Employe/verificationDeLaSuppression.jsp").forward(request, response);
    }

    // --- MODIFICATION ---
    private void modifierEmploye(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employe employe = employeDAO.rechercherParId(id);

        employe.setNom(request.getParameter("nom"));
        employe.setPrenom(request.getParameter("prenom"));
        employe.setAdresse(request.getParameter("adresse"));
        employe.setNumero(request.getParameter("numero"));
        employe.setEmail(request.getParameter("email"));
        employe.setDepartement(departementDAO.rechercherParId(Integer.parseInt(request.getParameter("id_departement"))));
        employe.setSexe(Sexe.valueOf(request.getParameter("sexe")));
        employe.setGrade(Grade.valueOf(request.getParameter("grade")));

        employeDAO.creerOuModifierEmploye(employe);
        request.setAttribute("messageSucces", "Employé modifié avec succès");
        request.getRequestDispatcher("Employe/afficheEmploye.jsp").forward(request, response);
    }

    // --- CHANGER ROLE ---
    private void changerRole(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employe employe = employeDAO.rechercherParId(id);

        int roleId = Integer.parseInt(request.getParameter("role"));
        Role role = roleDAO.rechercherRole(roleId);

        if (role.getId_role().equals(employe.getRole().getId_role())) {
            request.setAttribute("messagePasDeModificationRole", "Vous n'avez effectué aucun changement");
        } else {
            employe.setRole(role);
            employeDAO.creerOuModifierEmploye(employe);
            request.setAttribute("messageRoleChanged", "Le rôle de l'employé a été changé");
        }
        request.getRequestDispatcher("Employe/changerLeRole.jsp").forward(request, response);
    }

    // --- RECHERCHE ---
    private void rechercherEmploye(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String matriculeStr = request.getParameter("matricule");
        try {
            int matricule = Integer.parseInt(matriculeStr);
            Employe employe = employeDAO.rechercherParId(matricule);
            if (employe != null) {
                request.setAttribute("employe", employe);
                request.getRequestDispatcher("Employe/afficheEmploye.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("errorMessageEmployeNotFound", "Employé introuvable");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessageMatriculeNotInt", "Matricule invalide");
        }
        request.getRequestDispatcher("Employe/rechercheMatricule.jsp").forward(request, response);
    }

    // --- VERIFICATION MODIFICATION ---
    private void verifierModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employe employe = employeDAO.rechercherParId(id);

        List<String> changes = new ArrayList<>();

        String nom = request.getParameter("nom");
        if (!nom.equals(employe.getNom())) {
            changes.add("Nom : " + employe.getNom() + " → " + nom);
        }

        String prenom = request.getParameter("prenom");
        if (!prenom.equals(employe.getPrenom())) {
            changes.add("Prénom : " + employe.getPrenom() + " → " + prenom);
        }

        String adresse = request.getParameter("adresse");
        if (!adresse.equals(employe.getAdresse())) {
            changes.add("Adresse : " + employe.getAdresse() + " → " + adresse);
        }

        String numero = request.getParameter("numero");
        if (!numero.equals(employe.getNumero())) {
            changes.add("Téléphone : " + employe.getNumero() + " → " + numero);
        }

        String email = request.getParameter("email");
        if (!email.equals(employe.getEmail())) {
            changes.add("Email : " + employe.getEmail() + " → " + email);
        }

        int idDep = Integer.parseInt(request.getParameter("id_departement"));
        if (employe.getDepartement() == null || idDep != employe.getDepartement().getId_departement()) {
            Departement oldDep = employe.getDepartement();
            Departement newDep = departementDAO.rechercherParId(idDep);
            changes.add("Département : " + (oldDep != null ? oldDep.getNom() : "Non défini")
                    + " → " + (newDep != null ? newDep.getNom() : "Non défini"));
        }

        Sexe sexe = Sexe.valueOf(request.getParameter("sexe"));
        if (!sexe.equals(employe.getSexe())) {
            changes.add("Sexe : " + employe.getSexe() + " → " + sexe);
        }

        Grade grade = Grade.valueOf(request.getParameter("grade"));
        if (!grade.equals(employe.getGrade())) {
            changes.add("Grade : " + employe.getGrade() + " → " + grade);
        }

        float salaire = Float.parseFloat(request.getParameter("salaire"));
        if (salaire != employe.getSalaire()) {
            changes.add("Salaire : " + employe.getSalaire() + " → " + salaire);
        }

        // --- Résultat ---
        if (changes.isEmpty()) {
            request.setAttribute("messagePasDeModification", "Vous n'avez effectué aucun changement");
            request.getRequestDispatcher("Employe/modifierEmploye.jsp").forward(request, response);
        } else {
            request.setAttribute("changes", changes);
            request.setAttribute("employe", employe);
            request.getRequestDispatcher("Employe/verificationDesChangements.jsp").forward(request, response);
        }
    }

}
*/