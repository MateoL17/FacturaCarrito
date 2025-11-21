package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtenemos la conexion
        Connection conn = (Connection) req.getAttribute("conn");
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
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtenemos la conexion
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoServices services = new ProductoServiceJdbcImpl(conn);

        String nombre = req.getParameter("nombre");
        Long categoriaId;
        try {
            categoriaId = Long.parseLong(req.getParameter("categoria"));
        } catch (NumberFormatException e) {
            categoriaId = 0L;
        }
        Integer stock;
        try {
            stock = Integer.parseInt(req.getParameter("stock"));
        } catch (NumberFormatException e) {
            stock = 0;
        }
        String precioParam = req.getParameter("precio");
        Double precio = null;

        String descripcion = req.getParameter("descripcion");
        String condicionParam = req.getParameter("condicion");
        String fecha_elaboracion = req.getParameter("fecha_elaboracion");
        String fecha_caducidad = req.getParameter("fecha_caducidad");

        // Capturamos mapeando todos los errores en una lista tipo Map
        Map<String, String> errores = new HashMap<>();
        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "El nombre no puede estar vacio");
        }
        if (categoriaId.equals(0L)) {
            errores.put("categoria", "El categoria no puede estar vacio");
        }
        if (stock.equals(0)) {
            errores.put("stock", "El stock no puede estar vacio");
        }
        if (precioParam == null || precioParam.trim().isEmpty()) {
            errores.put("precio", "El precio no puede estar vacio");
        } else {
            precioParam = precioParam.trim().replace(",",".");
            try {
                precio = Double.valueOf(precioParam);
                if (precio <= 0) {
                    errores.put("precio", "El precio debe ser mayor a 0");
                }
            } catch (NumberFormatException e) {
                errores.put("precio", "El precio debe ser un número valido");
            }
        }
        if (condicionParam == null || condicionParam.isBlank()) {
            errores.put("condicion", "La condición no puede estar vacia");
        }
        if (fecha_elaboracion == null || fecha_elaboracion.isBlank()) {
            errores.put("fecha_elaboracion", "La fecha de elaboracion no puede estar vacia");
        }
        if (fecha_caducidad == null || fecha_caducidad.isBlank()) {
            errores.put("fecha_caducidad", "La fecha de caducidad no puede estar vacia");
        }

        // Transformamos la fecha
        LocalDate fechaElaboracion, fechaCaducidad;
        try {
            fechaElaboracion = LocalDate.parse(fecha_elaboracion, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            fechaCaducidad = LocalDate.parse(fecha_caducidad, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            fechaElaboracion = null;
            fechaCaducidad = null;
        }

        // Procesamos el id del producto
        Long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        // Instaciamos el nuevo objeto con los datos capturados
        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombre(nombre);
        // Instaciamos el nuevo objeto de categoria
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);

        producto.setCategoria(categoria);
        producto.setStock(stock);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);

        // Parseamos condicion solo si no es null y no está vacío
        if (condicionParam != null && !condicionParam.trim().isEmpty()) {
            try {
                producto.setCondicion(Integer.parseInt(condicionParam.trim()));
            } catch (NumberFormatException e) {
                errores.put("condicion", "El código debe ser un número válido");
            }
        }

        producto.setFechaElaboracion(fechaElaboracion);
        producto.setFechaCaducidad(fechaCaducidad);

        // Verificamos si la lista de errores esta vacia si es verdadero llamamos al metodo guardar
        // caso contrario seteamos los errores
        if (errores.isEmpty()) {
            services.guardar(producto);
            resp.sendRedirect(req.getContextPath()+"/productos");
        } else {
            req.setAttribute("errores", errores);
            req.setAttribute("categorias", services.listarCategorias());
            req.setAttribute("producto", producto);
            getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
        }
    }
}
