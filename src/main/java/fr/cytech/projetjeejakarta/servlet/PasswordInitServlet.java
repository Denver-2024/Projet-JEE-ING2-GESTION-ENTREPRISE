package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(value = "/init-passwords", loadOnStartup = 1)
public class PasswordInitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("=== INITIALISATION DES MOTS DE PASSE ===");

        EmployeDAO employeDAO = new EmployeDAO();
        boolean needsUpdate = false;

        // Vérifie si au moins un employé n'a pas de hash BCrypt
        for (int i = 1; i <= 20; i++) {
            Employe employe = employeDAO.rechercherParId(i);
            if (employe != null && (employe.getPassword() == null ||
                    !employe.getPassword().startsWith("$2a$"))) {
                needsUpdate = true;
                break;
            }
        }

        // Si besoin, régénère tous les mots de passe
        if (needsUpdate) {
            regeneratePasswords(employeDAO);
        } else {
            System.out.println("Les mots de passe sont déjà hashés avec BCrypt");
        }
    }

    private void regeneratePasswords(EmployeDAO employeDAO) {
        try {
            // Test de génération
            String testHash = PasswordUtil.hashPassword("JEE_Killers");
            System.out.println("Hash test pour 'JEE_Killers': " + testHash);
            System.out.println("Vérification test: " + PasswordUtil.checkPassword("JEE_Killers", testHash));

            // Met à jour tous les employés
            for (int i = 1; i <= 20; i++) {
                Employe employe = employeDAO.rechercherParId(i);
                if (employe != null) {
                    // Utilise EntityManager directement pour la mise à jour
                    jakarta.persistence.EntityManager em = fr.cytech.projetjeejakarta.util.JpaUtil.getEntityManagerFactory().createEntityManager();
                    try {
                        em.getTransaction().begin();
                        Employe managedEmploye = em.find(Employe.class, i);
                        String hashedPassword = PasswordUtil.hashPassword("JEE_Killers");
                        managedEmploye.setPassword(hashedPassword);
                        em.getTransaction().commit();
                        System.out.println("✓ Employé " + i + " - " + managedEmploye.getNom() + " : mot de passe hashé");
                    } catch (Exception e) {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                        }
                        System.err.println("✗ Erreur pour employé " + i + ": " + e.getMessage());
                    } finally {
                        em.close();
                    }
                }
            }
            System.out.println("=== INITIALISATION TERMINÉE ===");
        } catch (Exception e) {
            System.err.println("ERREUR lors de l'initialisation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}