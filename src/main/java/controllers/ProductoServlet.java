package controllers;
/*
 * Servlet para mostrar el listado de productos
 * Muestra información diferente según si el usuario está autenticado o no
 * Autor: ITQ
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.LoginService;
import services.LoginServiceSessionImpl;
import services.ProductoServices;
import services.ProductosServicesImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoServices service = new ProductosServicesImpl();
        List<Producto> productos = service.listar();

        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=utf-8>");
            out.println("<title>Listado de Productos</title>");
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Listado de Productos!</h1>");
            if(usernameOptional.isPresent()) {
                out.println("<div style='color: blue;'>Hola " + usernameOptional.get() + "</div>");
            }
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>nombre</th>");
            out.println("<th>tipo</th>");
            if(usernameOptional.isPresent()) {
                out.println("<th>precio</th>");
                out.println("<th>Opciones</th>");
            }
            out.println("</tr>");
            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");
                if(usernameOptional.isPresent()) {
                    out.println("<td>" + p.getPrecio() + "</td>");
                    out.println("<td><a href=\""
                            + req.getContextPath()
                            + "/agregar-carro?id="
                            + p.getIdProducto()
                            + "\" class=\"nav-button\">Agregar Producto al carro</a></td>");
                }
                out.println("</tr>");
            });
            out.println("</table>");
            out.println("<br>");
            out.println("<div class='nav-buttons'>");
            out.println("<a href='" + req.getContextPath() + "/index.html' class='nav-button secondary'>Volver</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
