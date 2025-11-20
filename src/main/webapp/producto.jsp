<%--
  Created by IntelliJ IDEA.
  User: ITQ
  Date: 20/11/2025
  Time: 08:21 a. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
    import="java.util.*, models.*"%>
<%----%>
<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>
<html>
<head>
    <title>Listado Productos</title>
</head>
<body>
<h1>Listado de Productos</h1>
<% if (username.isPresent()) {%>
<div>Hola <%=username.get()%>, Bienvenido!</div>
<p><a href="<%=request.getContextPath()%>">Crear un producto</a></p>
<%}%>
<table>
    <tr>
        <th>ID Producto</th>
        <th>Nombre del Producto</th>
        <th>Categoría</th>
        <th>Stock</th>
        <th>Descripción</th>
        <th>Código</th>
        <th>Fecha Elaboración</th>
        <th>Fecha Caducidad</th>
        <th>Condición</th>
        <%if (username.isPresent()) {%>
        <th>Precio</th>
        <th>Acción</th>
        <%}%>
    </tr>
    <% for (Producto p : productos) {%>
    <tr>
        <td><%=p.getIdProducto()%></td>
        <td><%=p.getNombre()%></td>
        <td><%=p.getCategoria().getNombre()%></td>
        <td><%=p.getStock()%></td>
        <td><%=p.getDescripcion()%></td>
        <td><%=p.getCondicion()%></td>
        <%if(username.isPresent()) {%>
        <td><%=p.getPrecio()%></td>
        <td><a href="<%=request.getContextPath()%>/agregar-carro?id=<%=p.getIdProducto()%>">Agregar al Carro</a></td>
        <%}%>
    </tr>
    <%}%>
</table>

</body>
</html>
