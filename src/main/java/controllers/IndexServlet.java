package controllers;
/**
 * Servlet principal que maneja la página de inicio
 * Muestra un contador de inicios de sesión y enlaces a otras funcionalidades
 * Autor: ITQ
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet({"/index.html", "/index"})
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener el contador del contexto de la aplicación
        Integer loginCounter = (Integer) getServletContext().getAttribute("loginCounter");
        if (loginCounter == null) {
            loginCounter = 0;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Cabeceras Http</title>");
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Manejo de cookies Http</h3>");
            out.println("<div style='background-color: #f0f0f0; padding: 10px; margin-bottom: 20px;'>");
            out.println("<strong>Contador de inicios de sesión: " + loginCounter + "</strong>");
            out.println("</div>");
            out.println("<ul>");
            out.println("<li><a href='productos'>mostrar productos html</a></li>");
            out.println("<br>");
            out.println("<li><a href='login'>login</a></li>");
            out.println("<br>");
            out.println("<li><a href='logout'>logout</a></li>");
            out.println("<br>");
            out.println("<li><a href='ver-carro'>ver carro</a></li>");
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
