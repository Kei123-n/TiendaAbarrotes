package com.tienda.model;

public class Producto {
    private String nombre;
    private Double precio;
    private int cantidad;  // Añadir cantidad al producto

    // Constructor
    public Producto(String nombre, Double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Método para verificar si el producto está por agotarse
    public boolean estaPorAgotarse() {
        return cantidad < 5; // Umbral de 5 productos para la alerta
    }
}
