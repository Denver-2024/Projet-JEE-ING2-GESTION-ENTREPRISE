<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Tableau de Bord</title>
    <link rel="stylesheet" type="text/css" href="CSS/style.css">
    <style>
        header {
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
    </style>
</head>
<body>
<header>
    <h1>Tableau de Bord - Gestion Entreprise</h1>
    <div>
        <span>Bienvenue, ${sessionScope.employe.prenom} ${sessionScope.employe.nom}</span>
        <span style="margin-left: 1rem;">Rôle: ${sessionScope.role}</span>
        <a href="${pageContext.request.contextPath}/logout" style="color: white; margin-left: 1rem;">Déconnexion</a>
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
        <p>Interface de gestion des employés à implémenter...</p>
        <ul>
            <li>Ajouter un nouvel employé</li>
            <li>Modifier les informations d'un employé</li>
            <li>Supprimer un employé</li>
            <li>Lister tous les employés</li>
            <li>Rechercher un employé</li>
        </ul>
    </div>

    <div id="departements" style="display:none;">
        <h3>Gestion des Départements</h3>
        <p>Interface de gestion des départements à implémenter...</p>
        <ul>
            <li>Ajouter un département</li>
            <li>Lister les départements</li>
            <li>Affecter un employé à un département</li>
            <li>Visualiser les membres d'un département</li>
        </ul>
    </div>

    <div id="projets" style="display:none;">
        <h3>Gestion des Projets</h3>
        <p>Interface de gestion des projets à implémenter...</p>
        <ul>
            <li>Créer/supprimer un projet</li>
            <li>Modifier un projet</li>
            <li>Affecter des employés à un projet</li>
            <li>Suivre l'état d'avancement</li>
        </ul>
    </div>

    <div id="fiches-paie" style="display:none;">
        <h3>Fiches de Paie</h3>
        <p>Interface de gestion des fiches de paie à implémenter...</p>
        <ul>
            <li>Créer une fiche de paie</li>
            <li>Consulter les fiches de paie</li>
            <li>Générer une fiche de paie imprimable</li>
            <li>Rechercher les fiches par période</li>
        </ul>
    </div>

    <div id="roles" style="display:none;">
        <h3>Gestion des Rôles</h3>
        <p>Interface de gestion des rôles à implémenter...</p>
        <ul>
            <li>Gérer les rôles (Administrateur, RH, Chef de département, Chef de projet, Employé)</li>
            <li>Attribuer des autorisations aux rôles</li>
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