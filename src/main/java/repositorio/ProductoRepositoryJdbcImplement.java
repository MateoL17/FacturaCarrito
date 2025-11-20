package repositorio;

import models.Categoria;
import models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImplement implements Repository<Producto> {

    // Obtener la conexion a la BDD
    private Connection conn;

    // Obtengo mi conexión mediante el constructor
    public ProductoRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT p.*, c.nombreCategoria as categoria " + "FROM producto as p " +
                             "INNER JOIN categoria as c ON (p.idCategoria = c.id) " +
                             "ORDER BY p.id ASC")) {
            while (rs.next()) {
                Producto p = getProducto(rs);
                productos.add(p);
            }
        }
        return productos;
    }

    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT p.*, c.nombreCategoria as categoria " + "FROM producto as p " +
                        "INNER JOIN categoria as c ON (p.idCategoria = c.id) " +
                        "WHERE p.id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = getProducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql;
        if (producto.getIdProducto() != null && producto.getIdProducto() > 0) {
            sql = "UPDATE producto SET nombreProducto=?, idCategoria=?, stock=?, precio=?, descripcion=?, " +
                    "fecha_elaboracion=?, fecha_caducidad=? WHERE id=?";
        } else {
            sql = "INSERT INTO producto (nombreProducto, idCategoria, stock, precio, descripcion, " +
                    "fecha_elaboracion, fecha_caducidad, condicion) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getCategoria().getId());
            stmt.setInt(3, producto.getStock());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setString(5, producto.getDescripcion());

            if (producto.getIdProducto() != null && producto.getIdProducto() > 0) {
                // UPDATE
                stmt.setDate(6, Date.valueOf(producto.getFechaElaboracion()));
                stmt.setDate(7, Date.valueOf(producto.getFechaCaducidad()));
                stmt.setLong(8, producto.getIdProducto());
            } else {
                // INSERT
                stmt.setDate(6, Date.valueOf(producto.getFechaElaboracion()));
                stmt.setDate(7, Date.valueOf(producto.getFechaCaducidad()));
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from producto where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Producto getProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getLong("id"));
        p.setNombre(rs.getString("nombreProducto"));
        p.setStock(rs.getInt("stock"));
        p.setPrecio(rs.getDouble("precio"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setCondicion(rs.getInt("condicion"));
        p.setFechaElaboracion(rs.getDate("fecha_elaboracion").toLocalDate());
        p.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
        p.setTipo(rs.getString("categoria"));

        Categoria c = new Categoria();
        c.setId(rs.getLong("idCategoria"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);

        return p;
    }
}
