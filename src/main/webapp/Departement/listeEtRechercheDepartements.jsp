<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Departement" %>
<html>
<head>
    <title>Liste des départements</title>
    <style>
        header {
            background-color: #343a40;
            color: white;
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-radius: 10px 10px 0 0;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
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
<div class="page">
    <div class="container">
        <h2>Rechercher un Département</h2>
        <form action="../DepartementController" method="get">
            <input type="hidden" name="action" value="rechercher">
            <label for="nomRecherche">Nom :</label>
            <input type="text" name="nom" id="nomRecherche" required>
            <input type="submit" value="Rechercher">
        </form>
        <hr>
        <h2>Liste complète des départements</h2>

        <%
            String messageErreur = (String) request.getAttribute("messageErreur");
            String messageSucces = (String) request.getAttribute("messageSucces");

            if (messageErreur != null) {
        %>
        <p class="messageErreur"><%= messageErreur %></p>
        <% } else if (messageSucces != null) { %>
        <p class="messageSucces"><%= messageSucces %></p>
        <% } %>

        <%
            List<Departement> departements = (List<Departement>) request.getAttribute("departements");
            if (departements != null && !departements.isEmpty()) {
        %>
        <table>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Description</th>
                <th>Chef de département</th>
                <th>Actions</th>
            </tr>
            <% for (Departement d : departements) { %>
            <tr>
                <td><%= d.getId_departement() %></td>
                <td><%= d.getNom() %></td>
                <td><%= d.getDescription() %></td>
                <td><%= (d.getChefDepartement() != null ? d.getChefDepartement().getNom() : "Non défini") %></td>
                <td>
                    <a href="../DepartementController?action=modifier&id=<%= d.getId_departement() %>">Modifier</a>
                    <a href="../DepartementController?action=supprimer&id=<%= d.getId_departement() %>">Supprimer</a>
                    <a href="../DepartementController?action=employes&idDepartement=<%= d.getId_departement() %>">Voir employés</a>
                    <a href="../DepartementController?action=projets&idDepartement=<%= d.getId_departement() %>">Voir projets</a>
                </td>
            </tr>
            <% } %>
        </table>
        <% } else if (messageErreur == null) { %>
        <p class="message">Aucun département enregistré.</p>
        <% } %>

        <hr>
        <c:forEach var="autorisation" items="${sessionScope.autorisations}">
            <c:if test="${fn:contains(autorisation.nom, 'ajouter_un_departement')}">
                <form action="${pageContext.request.contextPath}/Employe/changerLeRole.jsp">
                    <a href="formulaireCreerDepartement.jsp">Créer un département</a>
                </form>
            </c:if>
        </c:forEach>

    </div>
</div>
</body>
</html>
