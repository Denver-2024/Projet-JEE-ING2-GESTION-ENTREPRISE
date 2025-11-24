<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>
<html>
<head>
    <title>Projets du département</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Projets du département</h2>
    <%
        List<Projet> projets = (List<Projet>) request.getAttribute("projets");
        if (projets != null && !projets.isEmpty()) {
    %>
    <table>
        <tr><th>ID</th><th>Nom</th><th>Description</th><th>État</th></tr>
        <%
            for (Projet p : projets) {
        %>
        <tr>
            <td><%= p.getIdProjet() %></td>
            <td><%= p.getNom() %></td>
            <td><%= p.getDescription() %></td>
            <td><%= p.getEtat() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <%
    } else {
    %>
    <p class="message">Aucun projet trouvé pour ce département.</p>
    <%
        }
    %>
    <a href="../DepartementController?action=liste">Retour aux départements</a>
</div>
</body>
</html>
