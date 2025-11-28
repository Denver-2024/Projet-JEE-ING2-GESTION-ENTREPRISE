<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Employe" %>
<%
    Projet p=(Projet) request.getAttribute("projet");
%>

<html>
<head>
    <title>Liste des employés du projet</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">

    <hr>
    <h2>Liste des employés affectés au projet</h2>
    <%
        String messageErreur =(String) request.getAttribute("messageErreur");
        String messageSucces =(String) request.getAttribute("messageSucces");
        if (messageErreur!=null){%>
    <p class="messageErreur"><%=messageErreur%></p>
    <%}
    else if (messageSucces!=null){%>
    <p class="messageSucces"><%=messageSucces%></p>
    <%
        }
        List<Employe> employes = (List<Employe>) request.getAttribute("employes");
        List<Employe> employesDepartement=(List<Employe>) request.getAttribute("employesDepartement");
        if (employes != null && !employes.isEmpty()) {
    %>
    <table>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Grade</th>
            <th>Action</th>
        </tr>
        <%
            for (Employe e : employes) {
        %>
        <tr>
            <td><%= e.getId_employe() %></td>
            <td><%= e.getNom() %></td>
            <td><%= e.getPrenom() %></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getGrade() %></td>
            <td>
                <a href="../ProjetController?action=enleverEmploye&idProjet=<%=p.getIdProjet()%>&idEmploye=<%= e.getId_employe()%>">Détacher</a><br>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%
        }
    %>
    <hr>
    <h2>Affecter un employé du département au projet</h2>
    <form action="../ProjetController" method="get">
        <input type="hidden" name="action" value="affecterEmploye">
        <input type="hidden" name="idProjet" value="<%=p.getIdProjet()%>">
    <%
        if (employesDepartement!=null && !employesDepartement.isEmpty()){
            %>
    <label for="employe_a_affecter">Selectionner l'employé: </label>
    <select name="idEmploye" id="employe_a_affecter">
    <%
            for (Employe e: employesDepartement){

            %>
        <option value="<%=e.getId_employe()%>"><%=e.getNom()%>, <%=e.getPrenom()%></option>
        <%
            }

            }
        %>
    </select>
        <input type="submit" value="Enregistrer">
    </form>
    <a href="../ProjetController?action=liste">Retour à la liste des projets</a>
</div>
</body>
</html>
