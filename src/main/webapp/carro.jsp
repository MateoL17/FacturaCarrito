<%--
  Created by IntelliJ IDEA.
  User: ITQ
  Date: 13/11/2025
  Time: 08:24 a. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.DetalleCarro" %>
<%@ page import="models.ItemCarro" %>
<%@ page import="java.text.DecimalFormat" %>
<%---Traemos la sesion Scriplet---%>
<%
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
    DecimalFormat df = new DecimalFormat("#,##0.00");
%>

<html>
<head>
    <title>Carro de Compras</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="main-container">
    <h1>Carro de Compras</h1>

    <%
        if (detalleCarro == null || detalleCarro.getItems().isEmpty()) {%>
    <p class="error-message">Lo sentimos no hay productos en el carro de compras</p>
    <%} else {%>
    <table>
        <tr>
            <th>ID Producto</th>
            <th>Nombre</th>
            <th>Precio Unitario</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
            <th>IVA (15%)</th>
            <th>Total a Pagar (IVA incluido)</th>
        </tr>
        <%
            for (ItemCarro item : detalleCarro.getItems()) {%>
        <tr>
            <td><%=item.getProducto().getIdProducto()%></td>
            <td><%=item.getProducto().getNombre()%></td>
            <td>$<%=df.format(item.getProducto().getPrecio())%></td>
            <td><%=item.getCantidad()%></td>
            <td>$<%=df.format(item.getSubTotal())%></td>
            <td>$<%=df.format(item.getIva())%></td>
            <td>$<%=df.format(item.getTotalConIva())%></td>
        </tr>
        <%}%>
        <tr>
            <td colspan="4" style="text-align: right">Totales:</td>
            <td>$<%=df.format(detalleCarro.getTotal())%></td>
            <td>$<%=df.format(detalleCarro.getTotalIva())%></td>
            <td>$<%=df.format(detalleCarro.getTotalConIva())%></td>
        </tr>
    </table>
    <%}%>

    <div class="nav-buttons">
        <a href="<%=request.getContextPath()%>/productos" class="nav-button">SEGUIR COMPRANDO</a>
        <% if (detalleCarro != null && !detalleCarro.getItems().isEmpty()) { %>
        <a href="<%=request.getContextPath()%>/generar-factura" class="nav-button secondary">IMPRIMIR FACTURA</a>
        <% } %>
        <a href="<%=request.getContextPath()%>/index.html" class="nav-button secondary">Volver al Inicio</a>
    </div>
</div>
</body>
</html>