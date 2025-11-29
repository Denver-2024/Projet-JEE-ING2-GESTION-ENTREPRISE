<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Departement" %>
<html>
<head>
    <title>Résultat recherche département</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Résultat de la recherche</h2>

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
    <p class="message">Aucun département trouvé.</p>
    <% } %>

    <hr>
    <a href="formulaireCreerDepartement.jsp">Créer un département</a> |
    <a href="../DepartementController?action=liste">Retour à la liste des départements</a>
</div>
</body>
</html>
