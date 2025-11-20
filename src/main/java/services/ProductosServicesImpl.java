package services;

import models.Categoria;
import models.Producto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductosServicesImpl implements ProductoServices {

    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "Laptop", "Tecnología", 1200.00, 50,
                        "Laptop de alto rendimiento", LocalDate.of(2027, 12, 1), 1,
                        LocalDate.of(2025, 1, 15)),
                new Producto(2L, "Mouse", "Tecnología", 25.50, 200,
                        "Mouse óptico inalámbrico", LocalDate.of(2028, 5, 10), 1,
                        LocalDate.of(2024, 11, 20)),
                new Producto(3L, "Teclado", "Tecnología", 45.00, 180,
                        "Teclado mecánico retroiluminado", LocalDate.of(2028, 8, 3), 1,
                        LocalDate.of(2024, 12, 5)),
                new Producto(4L, "Monitor", "Tecnología", 300.00, 75,
                        "Monitor LED Full HD", LocalDate.of(2029, 2, 15), 1,
                        LocalDate.of(2025, 3, 10)),
                new Producto(5L, "Silla", "Oficina", 150.00, 40,
                        "Silla ergonómica de oficina", LocalDate.of(2030, 4, 20), 1,
                        LocalDate.of(2025, 6, 1))
        );
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream()
                .filter(p -> id != null && p.getIdProducto().equals(id))
                .findAny();
    }

    @Override
    public void guardar(Producto producto) {

    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public List<Categoria> listarCategorias() {
        return List.of();
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        return Optional.empty();
    }
}
