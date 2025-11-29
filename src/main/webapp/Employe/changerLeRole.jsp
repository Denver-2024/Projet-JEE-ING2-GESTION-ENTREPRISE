<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 22/11/2025
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Changer le rôle</title>

    <style>

        body{
            background-image: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca, #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff, #46eefa, #5ffbf1);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;

            font-family: 'Times New Roman', serif;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 50px;
        }



        form {
            margin-bottom: 30px;
        }
        label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type="submit"] {
            background-color: black;
            border: none;
            color: white;
            padding: 10px;
            font-size: 16px;
            width: 100%;
            cursor: pointer;
            border-radius: 5px;
        }
        input[type="submit"]:hover {
            background-color: #333;
        }
        .changeRole{
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

    </style>

</head>
<body>
<div class="changeRole">
    <h1>Changement du rôle de l'employé ${sessionScope.employeFoundMatricule.id_employe}</h1>



    <label>Rôle : </label>
    <form action="${pageContext.request.contextPath}/../EmployeController/ChangeRoleController" method="post">
        <select name="role" >
            <c:forEach var="r" items="${applicationScope.rolesFound}">
                <c:if test="${r.id_role == sessionScope.employeFoundMatricule.role.id_role}">
                    <option value="${r.id_role}" selected>
                            ${r.nom}
                    </option>
                </c:if>
            </c:forEach>

            <c:forEach var="r" items="${applicationScope.rolesFound}">
                <c:if test="${r.id_role != sessionScope.employeFoundMatricule.role.id_role}">
                    <option value="${r.id_role}">${r.nom}</option>
                </c:if>
            </c:forEach>
        </select><br><br>
        <input type="submit" value="Confirmer">
    </form>
    <c:if test="${not empty messagePasDeModificationRole}">
        <p style="color: green;">${messagePasDeModificationRole}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/Employe/afficheEmploye.jsp">
        <input type="submit" value="Annuler">
    </form>

    <c:if test="${not empty messageRoleChanged}">
        <p style="color: green;">${messageRoleChanged}</p>
    </c:if>
</div>
</body>
</html>
