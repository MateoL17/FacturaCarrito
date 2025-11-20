package services;
/**
 * Interfaz para servicios de productos
 * Define el contrato para operaciones relacionadas con productos
 * Autor: ITQ
 */

import models.Categoria;
import models.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoServices {
    List<Producto> listar();
    Optional<Producto> porId(Long id);
    void guardar(Producto producto);
    void eliminar(Long id);
    // Implementamos un metodo para listar una categoria y traer la categoria por id
    List<Categoria> listarCategorias();
    Optional<Categoria> porIdCategoria(Long id);
}
