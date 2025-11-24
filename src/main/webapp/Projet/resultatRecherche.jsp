<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>

<html>
<head>
    <title>Résultat de la recherche</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Résultat de la recherche</h2>
    <%
        List<Projet> projets = (List<Projet>) request.getAttribute("projets");
        if (projets != null && !projets.isEmpty()) {
            for (Projet p : projets) {
    %>
    <div class="projet">
        <b>Nom :</b> <%= p.getNom() %><br>
        <b>Description :</b> <%= p.getDescription() %><br>
        <b>État :</b> <%= p.getEtat() %><br>
        <b>Chef de projet :</b> <%= (p.getChefDeProjet() != null ? p.getChefDeProjet().getNom() : "Non défini") %><br>
    </div>
    <%
        }
    } else {
    %>
    <p class="message">Aucun projet trouvé pour ce critère.</p>
    <%
        }
    %>
    <a href="Projet/projetFormulaire.jsp">Retour au formulaire</a>
</div>
</body>
</html>
