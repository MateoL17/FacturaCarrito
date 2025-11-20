package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import models.Producto;
import services.ProductoServiceJdbcImpl;
import services.ProductoServices;
import services.ProductosServicesImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class ProductoFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtenemos la conexion
        Connection conn = (Connection) req.getServletContext().getAttribute("conn");
        ProductoServices services = new ProductoServiceJdbcImpl(conn);
        Long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }
        Producto producto = new Producto();
        producto.setCategoria(new Categoria());
        if (id > 0) {
            Optional<Producto> o = services.porId(id);
            if (o.isPresent()) {
                producto = o.get();
            }
        }
        req.setAttribute("categorias", services.listarCategorias());
        req.setAttribute("producto", producto);
        getServletContext().getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
    }
}
