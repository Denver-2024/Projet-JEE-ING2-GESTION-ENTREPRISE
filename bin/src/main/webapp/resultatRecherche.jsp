<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>
<html>
<head>
    <title>Résultat de la recherche</title>
    <style>
        #message {
            text-align: center;
            font-style: italic;
            margin-bottom: 15px;
        }
        a {
            text-decoration: none;
            display: block;
            text-align: center;
            margin-top: 15px;
            background-color: black;
            border: none;
            color: white;
            padding: 10px;
            font-size: 16px;
            width: 100%;
            cursor: pointer;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<h2>Résultat de la recherche</h2>
<%
    List<Projet> projets = (List<Projet>) request.getAttribute("projets");
    if (projets != null && !projets.isEmpty()) {
        for (Projet p : projets) {
%>
<div>
    <b>Nom :</b> <%= p.getNom() %><br>
    <b>Description :</b> <%= p.getDescription() %><br>
    <b>État :</b> <%= p.getEtat() %><br>
    <b>Chef de projet :</b> <%= (p.getChefDeProjet() != null ? p.getChefDeProjet().getNom() : "Non défini") %><br>
    <hr>
</div>
<%
    }
} else {
%>
<p id="message">Aucun projet trouvé pour ce critère.</p>
<%
    }
%>
<a href="projetFormulaire.jsp">Retour au formulaire</a>
</body>
</html>
