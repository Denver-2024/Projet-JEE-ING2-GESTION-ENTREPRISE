<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Departement" %>
<html>
<head>
    <title>Liste des départements</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Liste complète des départements</h2>
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
        <%
            for (Departement d : departements) {
        %>
        <tr>
            <td><%= d.getId_departement() %></td>
            <td><%= d.getNom() %></td>
            <td><%= d.getDescription() %></td>
            <td><%= (d.getChefDepartement() != null ? d.getChefDepartement().getNom() : "Non défini") %></td>
            <td>
                <a href="../DepartementController?action=modifier&id=<%= d.getId_departement() %>">Modifier</a>
                <a href="../DepartementController?action=supprimer&id=<%= d.getId_departement() %>">Supprimer</a>
                <a href="../DepartementController?action=employes&nom=<%= d.getNom() %>">Voir employés</a>
                <a href="../DepartementController?action=projets&nom=<%= d.getNom() %>">Voir projets</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%
    } else {
    %>
    <p class="message">Aucun département enregistré.</p>
    <%
        }
    %>
    <a href="Departement/departementFormulaire.jsp">Créer un département</a>
</div>
</body>
</html>
