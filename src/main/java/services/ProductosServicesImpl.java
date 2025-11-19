package services;
/*
 * Implementación del servicio de productos
 * Proporciona datos de ejemplo para demostración
 * Autor: ITQ
 */

import models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductosServicesImpl implements ProductoServices {
    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "Laptop", "Tecnología", 1200.00),
                new Producto(2L, "Mouse", "Tecnología", 25.50),
                new Producto(3L, "Teclado", "Tecnología", 45.00),
                new Producto(4L, "Monitor", "Tecnología", 300.00),
                new Producto(5L, "Silla", "Oficina", 150.00)
                new Producto(6L, "as", "Tecnologia", 20.50, 400, "Ds", -2026, 1, 11-11-2025)
        );
    }

    @Override
    public Optional<Producto> porId(Long id) {
        /*
        * Stream en java es convertir una lista en una secuencia de elementos sobre
        * la cual se pueden aplicar operaciones funcionales como filter, map, collect
        * filter: aqui se filtra los elementos del stream
        * p -> representa cada producto de la lista
        * p.getId().equals(id) compara el id del producto con el id que recibimos como parametro
        * Si el id coincide, el producto pasa el filtro; sino, se descarta.
        * findAny(): intenta encontrar un elemento cualquiera del stream que cumpla la condicion
        * si lo encuentra devuelve un Optional<Producto>,
        * sino le encuentra devuelve Optional.empty()
        * */

        return listar().stream().filter(p -> p.getIdProducto().equals(id)).findAny();
    }
}
