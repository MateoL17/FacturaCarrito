<%--
  Created by IntelliJ IDEA.
  User: ITQ
  Date: 21/11/2025
  Time: 08:42 a. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, java.time.format.*,models.*" %>
<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
    Producto producto = (Producto) request.getAttribute("producto");
    String fechaElaboracion = producto.getFechaElaboracion()!= null?
            producto.getFechaElaboracion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):"";
    String fechaCaducidad = producto.getFechaCaducidad()!= null?
            producto.getFechaCaducidad().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):"";

    // Variables para evitar problemas de null en el textarea
    String descripcionValue = producto.getDescripcion() != null ? producto.getDescripcion() : "";
%>

<html>
<head>
    <title>Formulario Productos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<form action="<%=request.getContextPath()%>/productos/form" method="post">
    <div>
        <label for="nombre">Nombre: </label>
        <div>
            <input type="text" name="nombre" id="nombre" value="<%= producto.getNombre() != null ? producto.getNombre() : "" %>">
        </div>
        <%
            if (errores != null && errores.containsKey("nombre")){%>
        <div style="color: red"><%= errores.get("nombre") %></div>
        <%}%>
    </div>

    <div>
        <label for="categoria">Categoria: </label>
        <div>
            <select name="categoria" id="categoria">
                <option value="">------Seleccionar-------</option>
                <% for (Categoria c: categorias){ %>
                <option value="<%=c.getId()%>"
                        <%=c.getId().equals(producto.getCategoria().getId())?"selected": ""%>><%=c.getNombre()%></option>
                <%}%>
            </select>
        </div>
        <% if (errores != null && errores.containsKey("categoria")){%>
        <div style="color: red"><%=errores.get("categoria")%></div>
        <%}%>
    </div>

    <div>
        <label for="stock">Ingrese el stock: </label>
        <div>
            <input type="number" name="stock" id="stock" value="<%= producto.getStock() != 0 ? producto.getStock() : "" %>">
        </div>
        <% if (errores != null && errores.containsKey("stock")){%>
        <div style="color: red"><%=errores.get("stock")%></div>
        <%}%>
    </div>

    <div>
        <label for="precio">Precio: </label>
        <div>
            <input type="number" step="0.01" name="precio" id="precio" value="<%= producto.getPrecio() != 0 ? producto.getPrecio() : "" %>">
        </div>
        <% if (errores != null && errores.containsKey("precio")){%>
        <div style="color: red"><%=errores.get("precio")%></div>
        <%}%>
    </div>

    <div>
        <label for="descripcion">Descripción: </label>
        <div>
            <textarea name="descripcion" id="descripcion" cols="30" rows="10"><%= descripcionValue %></textarea>
        </div>
    </div>

    <div>
        <label for="condicion">Código: </label>
        <div>
            <input type="text" name="condicion" id="condicion" value="<%= producto.getCondicion() != 0 ? producto.getCondicion() : "" %>">
        </div>
        <% if (errores != null && errores.containsKey("condicion")){%>
        <div style="color: red"><%=errores.get("condicion")%></div>
        <%}%>
    </div>

    <div>
        <label for="fecha_elaboracion">Fecha de elaboración: </label>
        <div>
            <input type="date" name="fecha_elaboracion" id="fecha_elaboracion" value="<%= fechaElaboracion %>">
        </div>
        <% if (errores != null && errores.containsKey("fecha_elaboracion")){%>
        <div style="color: red"><%=errores.get("fecha_elaboracion")%></div>
        <%}%>
    </div>

    <div>
        <label for="fecha_caducidad">Fecha de caducidad: </label>
        <div>
            <input type="date" name="fecha_caducidad" id="fecha_caducidad" value="<%= fechaCaducidad %>">
        </div>
        <% if (errores != null && errores.containsKey("fecha_caducidad")){%>
        <div style="color: red"><%=errores.get("fecha_caducidad")%></div>
        <%}%>
    </div>

    <div>
        <input type="submit" value="<%=(producto.getIdProducto()!=null && producto.getIdProducto()>0)?"Editar":"Crear"%>">
        <input type="hidden" name="id" value="<%=producto.getIdProducto() != null ? producto.getIdProducto() : ""%>">
    </div>
</form>

</body>
</html>