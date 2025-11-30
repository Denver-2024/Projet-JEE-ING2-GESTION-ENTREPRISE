package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private EmployeDAO employeDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        employeDAO = new EmployeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        StringBuilder jsonResponse = new StringBuilder();

        try {
            // Lire le corps de la requête JSON
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            System.out.println("=== DEBUG ChangePasswordServlet ===");
            System.out.println("Corps de la requête: " + requestBody.toString());

            // Parser le JSON manuellement (simple)
            String body = requestBody.toString();
            String currentPassword = extractValue(body, "currentPassword");
            String newPassword = extractValue(body, "newPassword");
            String confirmPassword = extractValue(body, "confirmPassword");

            System.out.println("currentPassword: " + currentPassword);
            System.out.println("newPassword: " + newPassword);
            System.out.println("confirmPassword: " + confirmPassword);

            // Récupérer l'employé connecté
            Employe employe = (Employe) session.getAttribute("employe");
            if (employe == null) {
                System.out.println("Employé null dans la session");
                jsonResponse.append("{\"success\": false, \"message\": \"Session expirée. Veuillez vous reconnecter.\"}");
                out.print(jsonResponse.toString());
                return;
            }
            System.out.println("Employé ID: " + employe.getId_employe());

            // Validation
            if (currentPassword == null || currentPassword.trim().isEmpty() ||
                    newPassword == null || newPassword.trim().isEmpty() ||
                    confirmPassword == null || confirmPassword.trim().isEmpty()) {

                System.out.println("Validation failed - empty fields");
                jsonResponse.append("{\"success\": false, \"message\": \"Tous les champs sont obligatoires.\"}");
                out.print(jsonResponse.toString());
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                jsonResponse.append("{\"success\": false, \"message\": \"Les nouveaux mots de passe ne correspondent pas.\"}");
                out.print(jsonResponse.toString());
                return;
            }

            if (newPassword.length() < 6) {
                jsonResponse.append("{\"success\": false, \"message\": \"Le nouveau mot de passe doit contenir au moins 6 caractères.\"}");
                out.print(jsonResponse.toString());
                return;
            }

            // Vérifier le mot de passe actuel
            Employe currentEmploye = employeDAO.rechercherParId(employe.getId_employe());
            if (currentEmploye == null) {
                jsonResponse.append("{\"success\": false, \"message\": \"Employé non trouvé.\"}");
                out.print(jsonResponse.toString());
                return;
            }

            // Vérifier le mot de passe actuel avec BCrypt
            String currentHashedPassword = currentEmploye.getPassword();
            if (currentHashedPassword == null || !BCrypt.checkpw(currentPassword, currentHashedPassword)) {
                jsonResponse.append("{\"success\": false, \"message\": \"Le mot de passe actuel est incorrect.\"}");
                out.print(jsonResponse.toString());
                return;
            }

            // Hasher le nouveau mot de passe
            String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // Mettre à jour le mot de passe
            boolean updateSuccess = employeDAO.updatePassword(employe.getId_employe(), hashedNewPassword);

            if (updateSuccess) {
                jsonResponse.append("{\"success\": true, \"message\": \"Mot de passe changé avec succès !\"}");
            } else {
                jsonResponse.append("{\"success\": false, \"message\": \"Erreur lors de la mise à jour du mot de passe.\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.append("{\"success\": false, \"message\": \"Une erreur technique est survenue: ").append(e.getMessage()).append("\"}");
        }

        out.print(jsonResponse.toString());
    }

    // Méthode utilitaire pour extraire les valeurs du JSON
    private String extractValue(String json, String key) {
        try {
            String searchKey = "\"" + key + "\":\"";
            int startIndex = json.indexOf(searchKey);
            if (startIndex == -1) return null;

            startIndex += searchKey.length();
            int endIndex = json.indexOf("\"", startIndex);
            if (endIndex == -1) return null;

            return json.substring(startIndex, endIndex);
        } catch (Exception e) {
            return null;
        }
    }
}