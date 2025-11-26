<%--
  Created by IntelliJ IDEA.
  User: fleefie
  Date: 11/26/25
  Time: 3:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Rechercher un Employé</h1>

<!-- Search Form -->
<form action="search-employe" method="post">
    <label>ID Employé :</label>
    <input type="number" name="id" />
    <br/><br/>

    <label>Nom :</label>
    <input type="text" name="nom" />
    <br/><br/>

    <label>ID Département :</label>
    <input type="number" name="departementId" />
    <br/><br/>

    <button type="submit">Rechercher</button>
</form>

<hr/>

<!-- Results Display -->
<h2>Résultats</h2>

<c:choose>
    <%-- Case: List is present but empty --%>
    <c:when test="${not empty resultats && resultats.size() == 0}">
        <p>Aucun employé trouvé avec ces critères.</p>
    </c:when>

    <%-- Case: List has data --%>
    <c:when test="${not empty resultats}">
        <table border="1" cellpadding="5">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Département</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="employe" items="${resultats}">
                <tr>
                    <td>${employe.id}</td> <!-- Assuming getId() exists -->
                    <td>${employe.nom}</td> <!-- Assuming getNom() exists -->
                    <td>
                        <c:choose>
                            <c:when test="${not empty employe.departement}">
                                ${employe.departement.nom} (ID: ${employe.departement.id_departement})
                            </c:when>
                            <c:otherwise>
                                N/A
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>

    <%-- Case: Initial load (no search performed yet) --%>
    <c:otherwise>
        <p>Veuillez utiliser le formulaire pour afficher des employés.</p>
    </c:otherwise>
</c:choose>

</body>
</html>