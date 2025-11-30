<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Tableau de Bord</title>
    <link rel="stylesheet" type="text/css" href="CSS/style.css">
    <style>
        body {
            background-image: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca, #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff, #46eefa, #5ffbf1);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
            font-family: 'Times New Roman', serif;
            margin: 0;
            padding: 0;
        }

        .dashboard-container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            margin: 20px;
            min-height: calc(100vh - 40px);
        }

        header {
            background-color: #343a40;
            color: white;
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-radius: 10px 10px 0 0;
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

        .btn-action {
            background-color: #2d2929;
            border: none;
            color: white;
            padding: 10px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            margin: 5px;
            display: inline-block;
        }

        .btn-action:hover {
            background-color: #333;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="dashboard-container">
    <header>
        <div style="display: flex; align-items: center; gap: 1rem;">
            <a href="${pageContext.request.contextPath}/dashboard" style="text-decoration: none; display: flex; align-items: center; gap: 1rem;">
                <img src="${pageContext.request.contextPath}/images/Logo.png"
                     alt="Logo Gestion Entreprise"
                     style="height: 60px; width: auto;">
                <h1 style="color: white; margin: 0;">Tableau de Bord - Gestion Entreprise</h1>
            </a>
        </div>
        <div>
            <span>Bienvenue, ${sessionScope.employe.prenom} ${sessionScope.employe.nom}</span>
            <span style="margin-left: 1rem;">Rôle: ${sessionScope.role}</span>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
        </div>
    </header>

    <section class="menu">
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
    </section>

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
                <c:if test="${hasRechercherEmploye || hasModifierEmploye || hasSupprimerEmploye}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Employe/rechercheMatricule.jsp'">Rechercher un employé par matricule</button></c:if>
                <c:if test="${hasAffecterProjet}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Employe/rechercherEmploye.jsp'">Rechercher un employé</button></c:if>

                <c:if test="${!hasEmployes}">
                    <li class="feature-disabled">Aucune autorisation pour la gestion des employés</li>
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

                <c:if test="${hasAjouterDepartement}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Departement/formulaireCreerDepartement.jsp'">Ajouter un département</button></c:if>
                <c:if test="${hasListerDepartements}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Departement/listeEtRechercheDepartements.jsp'">Lister/Rechercher les départements</button></c:if>

                <c:if test="${!hasDepartements}">
                    <li class="feature-disabled">Aucune autorisation pour la gestion des départements</li>
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

                <c:if test="${hasCreerProjet}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Projet/formulaireCreerProjet.jsp'">Créer un projet</button></c:if>
                <c:if test="${hasSuivreProjet}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/Projet/listeEtRechercheProjets.jsp'">Lister les projets</button></c:if>

                <c:if test="${!hasProjets}">
                    <li class="feature-disabled">Aucune autorisation pour la gestion des projets</li>
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


                <c:if test="${hasRechercherFiches}"><button class="btn-action" onclick="window.location.href='${pageContext.request.contextPath}/FicheDePaie/rechercherFichesDePaie.jsp'">Rechercher mes fiches de paie par période</button></c:if>

                <c:if test="${!hasFichesPaie}">
                    <li class="feature-disabled">Aucune autorisation pour la gestion des fiches de paie</li>
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