<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Tableau de Bord</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .header {
            background-color: #343a40;
            color: white;
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .menu {
            background-color: #f8f9fa;
            padding: 1rem;
            display: flex;
            flex-wrap: wrap;
            gap: 0.5rem;
        }
        .menu button {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-profil { background-color: #6c757d; color: white; }
        .btn-employes { background-color: #007bff; color: white; }
        .btn-departements { background-color: #28a745; color: white; }
        .btn-projets { background-color: #ffc107; color: black; }
        .btn-fiches-paie { background-color: #17a2b8; color: white; }
        .btn-roles { background-color: #dc3545; color: white; }
        .content {
            padding: 2rem;
        }
        .debug-info {
            background: #f8f9fa;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            border-left: 4px solid #007bff;
        }
        .autorisation-badge {
            background: #e9ecef;
            padding: 2px 6px;
            margin: 2px;
            border-radius: 3px;
            display: inline-block;
            font-size: 12px;
        }
        .feature-disabled {
            color: #6c757d;
            text-decoration: line-through;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Tableau de Bord - Gestion Entreprise</h1>
    <div>
        <span>Bienvenue, ${sessionScope.employe.prenom} ${sessionScope.employe.nom}</span>
        <span style="margin-left: 1rem;">Rôle: ${sessionScope.role}</span>
        <a href="${pageContext.request.contextPath}/logout" style="color: white; margin-left: 1rem;">Déconnexion</a>
    </div>
</div>

<div class="menu">
    <!-- Bouton profil toujours visible -->
    <button class="btn-profil" onclick="showSection('profil')">Mon Profil</button>

    <!-- Déterminer quels boutons afficher -->
    <c:set var="hasEmployes" value="false" />
    <c:set var="hasDepartements" value="false" />
    <c:set var="hasProjets" value="false" />
    <c:set var="hasFichesPaie" value="false" />
    <c:set var="hasRoles" value="false" />

    <c:forEach var="autorisation" items="${sessionScope.autorisations}">
        <c:if test="${fn:contains(autorisation.nom, 'employe')}"><c:set var="hasEmployes" value="true" /></c:if>
        <c:if test="${fn:contains(autorisation.nom, 'departement')}"><c:set var="hasDepartements" value="true" /></c:if>
        <c:if test="${fn:contains(autorisation.nom, 'projet')}"><c:set var="hasProjets" value="true" /></c:if>
        <c:if test="${fn:contains(autorisation.nom, 'fiche') or fn:contains(autorisation.nom, 'paie')}"><c:set var="hasFichesPaie" value="true" /></c:if>
        <c:if test="${fn:contains(autorisation.nom, 'gestion_des_roles')}"><c:set var="hasRoles" value="true" /></c:if>
    </c:forEach>

    <!-- Afficher les boutons (sans doublons) -->
    <c:if test="${hasEmployes}">
        <button class="btn-employes" onclick="showSection('employes')">Gestion Employés</button>
    </c:if>
    <c:if test="${hasDepartements}">
        <button class="btn-departements" onclick="showSection('departements')">Gestion Départements</button>
    </c:if>
    <c:if test="${hasProjets}">
        <button class="btn-projets" onclick="showSection('projets')">Gestion Projets</button>
    </c:if>
    <c:if test="${hasFichesPaie}">
        <button class="btn-fiches-paie" onclick="showSection('fiches-paie')">Fiches de Paie</button>
    </c:if>
    <c:if test="${hasRoles}">
        <button class="btn-roles" onclick="showSection('roles')">Gestion des Rôles</button>
    </c:if>
</div>

<div class="content">
    <h2>Bienvenue sur votre espace ${sessionScope.role}</h2>

    <!-- Debug: Afficher les autorisations (optionnel) -->
    <div class="debug-info">
        <h4>Vos autorisations:</h4>
        <c:forEach var="autorisation" items="${sessionScope.autorisations}">
            <span class="autorisation-badge">${autorisation.nom}</span>
        </c:forEach>
    </div>

    <p>Utilisez le menu ci-dessus pour accéder aux différentes fonctionnalités.</p>

    <!-- Sections à afficher selon le bouton cliqué -->
    <div id="profil" style="display:none;">
        <h3>Mon Profil</h3>
        <p><strong>ID:</strong> ${sessionScope.employe.id_employe}</p>
        <p><strong>Nom:</strong> ${sessionScope.employe.nom}</p>
        <p><strong>Prénom:</strong> ${sessionScope.employe.prenom}</p>
        <p><strong>Email:</strong> ${sessionScope.employe.email}</p>
        <p><strong>Grade:</strong> ${sessionScope.employe.grade}</p>
        <p><strong>Rôle:</strong> ${sessionScope.role}</p>
        <p><strong>Département:</strong> ${sessionScope.employe.departement.nom}</p>
    </div>

    <div id="employes" style="display:none;">
        <h3>Gestion des Employés</h3>
        <p>Fonctionnalités disponibles selon vos autorisations :</p>
        <ul>
            <!-- CORRECTION : On parcourt chaque autorisation pour vérifier si elle existe -->
            <c:set var="hasAjouterEmploye" value="false" />
            <c:set var="hasModifierEmploye" value="false" />
            <c:set var="hasSupprimerEmploye" value="false" />
            <c:set var="hasListerEmployes" value="false" />
            <c:set var="hasRechercherEmploye" value="false" />
            <c:set var="hasAffecterProjet" value="false" />

            <c:forEach var="autorisation" items="${sessionScope.autorisations}">
                <c:if test="${autorisation.nom == 'ajouter_un_nouvel_employe'}"><c:set var="hasAjouterEmploye" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'modifier_les_informations_d_un_employe'}"><c:set var="hasModifierEmploye" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'supprimer_un_employe'}"><c:set var="hasSupprimerEmploye" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'lister_tous_les_employes'}"><c:set var="hasListerEmployes" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'rechercher_un_employe_par_nom_prenom_ou_departement' or autorisation.nom == 'rechercher_un_employe_par_matricule'}"><c:set var="hasRechercherEmploye" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'affecter_un_employe_a_un_ou_plusieurs_projets'}"><c:set var="hasAffecterProjet" value="true" /></c:if>
            </c:forEach>

            <c:if test="${hasAjouterEmploye}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Employe/ajouterEmploye.jsp'">Ajouter un nouvel employé</button></c:if>
            <c:if test="${hasListerEmployes}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Employe/listerEmploye.jsp'">Lister tous les employés</button></c:if>
            <c:if test="${hasRechercherEmploye || hasModifierEmploye || hasSupprimerEmploye}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Employe/rechercheMatricule.jsp'">Rechercher un employé par matricule</button></li></c:if>
            <c:if test="${hasAffecterProjet}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Employe/rechercherEmploye.jsp'">Rechercher un employé</button></c:if>

            <c:if test="${!hasEmployes}">
                <li class="feature-disabled">⛔ Aucune autorisation pour la gestion des employés</li>
            </c:if>
        </ul>
    </div>

    <div id="departements" style="display:none;">
        <h3>Gestion des Départements</h3>
        <p>Fonctionnalités disponibles selon vos autorisations :</p>
        <ul>
            <c:set var="hasAjouterDepartement" value="false" />
            <c:set var="hasListerDepartements" value="false" />
            <c:set var="hasAffecterDepartement" value="false" />
            <c:set var="hasVisualiserMembres" value="false" />

            <c:forEach var="autorisation" items="${sessionScope.autorisations}">
                <c:if test="${autorisation.nom == 'ajouter_un_departement'}"><c:set var="hasAjouterDepartement" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'lister_les_departements'}"><c:set var="hasListerDepartements" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'affecter_un_employe_a_un_departement'}"><c:set var="hasAffecterDepartement" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'visualiser_les_membres_d_un_departement'}"><c:set var="hasVisualiserMembres" value="true" /></c:if>
            </c:forEach>

            <c:if test="${hasAjouterDepartement}"><li>Ajouter un département</li></c:if>
            <c:if test="${hasListerDepartements}"><li>Lister les départements</li></c:if>
            <c:if test="${hasAffecterDepartement}"><li>Affecter un employé à un département</li></c:if>
            <c:if test="${hasVisualiserMembres}"><li>Visualiser les membres d'un département</li></c:if>

            <c:if test="${!hasDepartements}">
                <li class="feature-disabled">⛔ Aucune autorisation pour la gestion des départements</li>
            </c:if>
        </ul>
    </div>

    <div id="projets" style="display:none;">
        <h3>Gestion des Projets</h3>
        <p>Fonctionnalités disponibles selon vos autorisations :</p>
        <ul>
            <c:set var="hasCreerProjet" value="false" />
            <c:set var="hasModifierProjet" value="false" />
            <c:set var="hasAffecterEmployesProjet" value="false" />
            <c:set var="hasSuivreProjet" value="false" />

            <c:forEach var="autorisation" items="${sessionScope.autorisations}">
                <c:if test="${autorisation.nom == 'creer_supprimer_un_projet'}"><c:set var="hasCreerProjet" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'modifier_un_projet'}"><c:set var="hasModifierProjet" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'affecter_des_employes_a_un_projet'}"><c:set var="hasAffecterEmployesProjet" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'suivre_l_etat_d_avancement_etat_en_cours_termine_annule'}"><c:set var="hasSuivreProjet" value="true" /></c:if>
            </c:forEach>

            <c:if test="${hasCreerProjet}"><li>Créer/supprimer un projet</li></c:if>
            <c:if test="${hasModifierProjet}"><li>Modifier un projet</li></c:if>
            <c:if test="${hasAffecterEmployesProjet}"><li>Affecter des employés à un projet</li></c:if>
            <c:if test="${hasSuivreProjet}"><li>Suivre l'état d'avancement</li></c:if>

            <c:if test="${!hasProjets}">
                <li class="feature-disabled">⛔ Aucune autorisation pour la gestion des projets</li>
            </c:if>
        </ul>
    </div>

    <div id="fiches-paie" style="display:none;">
        <h3>Fiches de Paie</h3>
        <p>Fonctionnalités disponibles selon vos autorisations :</p>
        <ul>
            <c:set var="hasCreerFichePaie" value="false" />
            <c:set var="hasConsulterFiches" value="false" />
            <c:set var="hasGenererFiche" value="false" />
            <c:set var="hasRechercherFiches" value="false" />

            <c:forEach var="autorisation" items="${sessionScope.autorisations}">
                <c:if test="${autorisation.nom == 'creer_une_fiche_de_paie_pour_un_employe_pour_un_mois_donne'}"><c:set var="hasCreerFichePaie" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'consulter_les_fiches_de_paie_d_un_employe'}"><c:set var="hasConsulterFiches" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'generer_une_fiche_de_paie_imprimable'}"><c:set var="hasGenererFiche" value="true" /></c:if>
                <c:if test="${autorisation.nom == 'rechercher_les_fiches_par_periode_ou_par_employe'}"><c:set var="hasRechercherFiches" value="true" /></c:if>
            </c:forEach>

            <c:if test="${hasCreerFichePaie}"><li>Créer une fiche de paie</li></c:if>
            <c:if test="${hasConsulterFiches}"><li>Consulter les fiches de paie</li></c:if>
            <c:if test="${hasGenererFiche}"><li>Générer une fiche de paie imprimable</li></c:if>
            <c:if test="${hasRechercherFiches}"><li>Rechercher les fiches par période</li></c:if>

            <c:if test="${!hasFichesPaie}">
                <li class="feature-disabled">⛔ Aucune autorisation pour la gestion des fiches de paie</li>
            </c:if>
        </ul>
    </div>

    <div id="roles" style="display:none;">
        <h3>Gestion des Rôles</h3>
        <p>Fonctionnalités disponibles selon vos autorisations :</p>
        <ul>
            <c:set var="hasGererRoles" value="false" />

            <c:forEach var="autorisation" items="${sessionScope.autorisations}">
                <c:if test="${autorisation.nom == 'gestion_des_roles_administrateur_administrateur_rh_chef_du_departement_chef_du_projet_employe'}"><c:set var="hasGererRoles" value="true" /></c:if>
            </c:forEach>

            <c:if test="${hasGererRoles}">
                <li>Gérer les rôles (Administrateur, RH, Chef de département, Chef de projet, Employé)</li>
                <li>Attribuer des autorisations aux rôles</li>
            </c:if>

            <c:if test="${!hasRoles}">
                <li class="feature-disabled">⛔ Aucune autorisation pour la gestion des rôles</li>
            </c:if>
        </ul>
    </div>
</div>

<script>
    function showSection(sectionId) {
        // Cacher toutes les sections
        document.querySelectorAll('.content > div').forEach(div => {
            div.style.display = 'none';
        });
        // Afficher la section sélectionnée
        const section = document.getElementById(sectionId);
        if (section) {
            section.style.display = 'block';
        }
    }

    // Afficher le profil par défaut
    document.addEventListener('DOMContentLoaded', function() {
        showSection('profil');
    });
</script>
</body>
</html>