package models;
/**
 * Clase modelo que representa un producto
 * Contiene información básica como id, nombre, tipo y precio
 * Autor: ITQ
 */

public class Producto {
    // Encapsulamos y declaramos las variables del objeto producto
    private Long idProducto;
    private String nombre;
    private String tipo;
    private double precio;

    /*
     *
     * */
    public Producto(){}

    /*
     *
     * */
    public Producto(Long idProducto, String nombre, String tipo, double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }

    /*
     *
     *  */
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
