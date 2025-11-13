<%--
  Created by IntelliJ IDEA.
  User: ITQ
  Date: 10/11/2025
  Time: 10:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%---Creación del formulario---%>
<html>
<head>
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="login-container">
    <h1 class="login-title">Iniciar Sesión</h1>
    <form class="login-form" action="login" method="post">
        <div class="form-group">
            <label for="user">Usuario</label>
            <input type="text" id="user" name="user" placeholder="Ingrese su usuario" required>
        </div>
        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" placeholder="Ingrese su contraseña" required>
        </div>
        <div class="form-group">
            <input type="submit" class="submit-btn" value="Entrar">
        </div>
    </form>
</div>
</body>
</html>