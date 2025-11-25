<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        }
        .menu button {
            margin-right: 0.5rem;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .admin-btn { background-color: #dc3545; color: white; }
        .rh-btn { background-color: #28a745; color: white; }
        .chef-btn { background-color: #17a2b8; color: white; }
        .employe-btn { background-color: #6c757d; color: white; }
        .content {
            padding: 2rem;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Tableau de Bord - Gestion Entreprise</h1>
    <div>
        <span>Bienvenue, ${sessionScope.employe.prenom} ${sessionScope.employe.nom}</span>
        <a href="${pageContext.request.contextPath}/logout" style="color: white; margin-left: 1rem;">Déconnexion</a>
    </div>
</div>

<div class="menu">
    <!-- Boutons visibles pour tous -->
    <button class="employe-btn" onclick="showSection('profil')">Mon Profil</button>

    <!-- Boutons selon le rôle -->
    <c:choose>
        <c:when test="${sessionScope.role == 'administrateur'}">
            <button class="admin-btn" onclick="showSection('employes')">Gestion Employés</button>
            <button class="admin-btn" onclick="showSection('departements')">Gestion Départements</button>
            <button class="admin-btn" onclick="showSection('projets')">Gestion Projets</button>
            <button class="admin-btn" onclick="showSection('fiches-paie')">Fiches de Paie</button>
        </c:when>
        <c:when test="${sessionScope.role == 'administrateur_rh'}">
            <button class="rh-btn" onclick="showSection('employes')">Gestion Employés</button>
            <button class="rh-btn" onclick="showSection('fiches-paie')">Fiches de Paie</button>
        </c:when>
        <c:when test="${sessionScope.role == 'chef_de_departement'}">
            <button class="chef-btn" onclick="showSection('mon-departement')">Mon Département</button>
            <button class="chef-btn" onclick="showSection('projets')">Projets</button>
        </c:when>
        <c:when test="${sessionScope.role == 'chef_de_projet'}">
            <button class="chef-btn" onclick="showSection('mon-projet')">Mon Projet</button>
        </c:when>
    </c:choose>
</div>

<div class="content">
    <h2>Bienvenue sur votre espace ${sessionScope.role}</h2>
    <p>Utilisez le menu ci-dessus pour accéder aux différentes fonctionnalités.</p>

    <!-- Sections à afficher selon le bouton cliqué -->
    <div id="profil" style="display:none;">
        <h3>Mon Profil</h3>
        <p>ID: ${sessionScope.employe.id_employe}</p>
        <p>Nom: ${sessionScope.employe.nom}</p>
        <p>Prénom: ${sessionScope.employe.prenom}</p>
        <p>Email: ${sessionScope.employe.email}</p>
        <p>Grade: ${sessionScope.employe.grade}</p>
        <p>Rôle: ${sessionScope.role}</p>
    </div>

    <!-- Autres sections à implémenter selon les besoins -->
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
</script>
</body>
</html>