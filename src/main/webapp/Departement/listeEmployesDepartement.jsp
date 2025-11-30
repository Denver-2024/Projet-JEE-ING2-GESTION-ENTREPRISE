<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Employe" %>
<html>
<head>
    <title>Employés du département</title>
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
        <h2>Employés du département</h2>

        <%
            String messageErreur = (String) request.getAttribute("messageErreur");
            String messageSucces = (String) request.getAttribute("messageSucces");
            Integer idDepartement = (Integer) request.getAttribute("idDepartement");

            if (messageErreur != null) {
        %>
        <p class="messageErreur"><%= messageErreur %></p>
        <% } else if (messageSucces != null) { %>
        <p class="messageSucces"><%= messageSucces %></p>
        <% } %>

        <%
            List<Employe> employes = (List<Employe>) request.getAttribute("employes");
            List<Employe> listeTotaleEmployes = (List<Employe>) request.getAttribute("listeTotaleEmployes");
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
            <% for (Employe e : employes) { %>
            <tr>
                <td><%= e.getId_employe() %></td>
                <td><%= e.getNom() %></td>
                <td><%= e.getPrenom() %></td>
                <td><%= e.getEmail() %></td>
                <td><%= e.getGrade() %></td>
                <td>

                    <a href="../DepartementController?action=retirerEmploye&idEmploye=<%= e.getId_employe() %>&idDepartement=<%= idDepartement %>">
                        Retirer
                    </a>
                </td>
            </tr>
            <% } %>
        </table>
        <% } else if (messageErreur == null) { %>
        <p class="message">Aucun employé trouvé pour ce département.</p>
        <% } %>

        <hr>


        <h3>Affecter un employé au département</h3>
        <%
        if (listeTotaleEmployes!=null && !listeTotaleEmployes.isEmpty()){
        %>
        <form action="../DepartementController" method="get">
            <input type="hidden" name="action" value="affecterEmploye">
            <input type="hidden" name="idDepartement" value="<%= idDepartement %>">

            <label for="employe_a_affecter">Sélectionner l'employé:</label>
                    <select name="idEmploye" id="employe_a_affecter" required>
                        <%
                            for (Employe e: listeTotaleEmployes){
                        %>
                        <option value="<%=e.getId_employe()%>"><%=e.getNom()%>, <%=e.getPrenom()%></option>
                        <%
                }
            %>
                    </select>

            <input type="submit" value="Affecter">
        </form>
        <%} else {%>
            <form action="../DepartementController" method="get">
                    <input type="hidden" name="action" value="affecterEmploye">
                    <input type="hidden" name="idDepartement" value="<%= idDepartement %>">

        <label for="employe_a_affecter">ID de l'employé: </label>
        <input type="text" name="idEmploye" id="employe_a_affecter" required>
                <%
                }
        %>

        <hr>
        <a href="../DepartementController?action=liste">Retour à la liste des départements</a>
    </div>
</div>
</body>
</html>
