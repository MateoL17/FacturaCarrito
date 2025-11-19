package controllers;
/**
 * Servlet para manejar el proceso de autenticación de usuarios
 * Controla el login/logout y mantiene un contador de sesiones activas
 * Autor: ITQ
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.LoginService;
import services.LoginServiceSessionImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    final static String USERNAME = "admin";
    final static String PASSWORD = "1234";

    // Contador de inicios de sesión
    private int loginCounter = 0;

    private LoginServiceSessionImpl authService = new LoginServiceSessionImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = authService.getUsername(req);

        if (usernameOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=utf-8>");
                out.println("<title>Hola " + usernameOptional.get() + "</title>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Hola " + usernameOptional.get() + " has iniciado sesión con éxito!</h1>");
                out.println("<p><a href='" + req.getContextPath() + "/index.html'>volver</a></p>");
                out.println("<p><a href='" + req.getContextPath() + "/logout'>cerrar sesión</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String password = req.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Incrementar contador
            loginCounter++;

            // Guardar contador en el contexto de la aplicación para que sea accesible desde todas las sesiones
            getServletContext().setAttribute("loginCounter", loginCounter);

            resp.sendRedirect(req.getContextPath() + "/productos");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Lo sentimos no esta autorizado para ingresar a esta página!");
        }
    }

    // Método para obtener el contador (puede ser estático o a través del contexto)
    public static int getLoginCounter(HttpServletRequest req) {
        Integer counter = (Integer) req.getServletContext().getAttribute("loginCounter");
        return counter != null ? counter : 0;
    }
}
