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
        List<Departement> departements = (List<Departement>) request.getAttribute("departements");
        if (departements != null && !departements.isEmpty()) {
            for (Departement d : departements) {
    %>
    <div class="projet">
        <b>Nom :</b> <%= d.getNom() %><br>
        <b>Description :</b> <%= d.getDescription() %><br>
        <b>Chef de département :</b> <%= (d.getChefDepartement() != null ? d.getChefDepartement().getNom() : "Non défini") %><br>
    </div>
    <%
        }
    } else {
    %>
    <p class="message">Aucun département trouvé.</p>
    <%
        }
    %>
    <a href="Departement/departementFormulaire.jsp">Retour au formulaire</a>
</div>
</body>
</html>
