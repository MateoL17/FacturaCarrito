package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Traemos la conexion
        Connection conn = (Connection) req.getAttribute ("conn");
        // Instaciamos el nuevo objeto
        ProductoServices service = new ProductoServiceJdbcImpl(conn);

        List<Producto> productos = service.listar();

        // Servicio de login
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<title>Listado de Productos</title>");
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Listado de Productos</h1>");

            usernameOptional.ifPresent(username -> {
                out.println("<div style='color: blue;'>Hola " + username + "</div>");
                out.println("<a href='productos/form'>Crear Producto</a>");
            });

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Tipo</th>");
            if (usernameOptional.isPresent()) {
                out.println("<th>Precio</th>");
                out.println("<th>Stock</th>");
                out.println("<th>Descripción</th>");
                out.println("<th>Fecha caducidad</th>");
                out.println("<th>Fecha elaboración</th>");
                out.println("<th>Opciones</th>");
            }
            out.println("</tr>");

            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");
                if (usernameOptional.isPresent()) {
                    out.println("<td>" + p.getPrecio() + "</td>");
                    out.println("<td>" + p.getStock() + "</td>");
                    out.println("<td>" + p.getDescripcion() + "</td>");
                    out.println("<td>" + p.getFechaCaducidad() + "</td>");
                    out.println("<td>" + p.getFechaElaboracion() + "</td>");
                    out.println("<td><a href='"
                            + req.getContextPath()
                            + "/agregar-carro?id="
                            + p.getIdProducto()
                            + "' class='nav-button'>Agregar al carro</a></td>");
                }
                out.println("</tr>");
            });

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }

        /*
        * Seteamos los atributos de productos y username para pasar al jsp
        * */
        req.setAttribute("productos", productos);
        req.setAttribute("username", usernameOptional);
        // Pasamos el Servlet
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/producto.jsp").forward(req, resp);
    }
}
