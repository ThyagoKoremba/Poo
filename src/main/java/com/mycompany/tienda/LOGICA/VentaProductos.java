/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda.LOGICA;

import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 *
 * @author xthy
 */
public class VentaProductos {
     @EmbeddedId
     private VentaProductoId id;
     
     @ManyToOne
     @MapsId("ventaId")
     @JoinColumn(name="producto_id")
     private Producto producto;
     
     private int cantidad;
     
     public double getSubtotal(){
         return cantidad*producto.getPrecio();
     }
    public VentaProductoId getId() {
        return id;
    }

    public void setId(VentaProductoId id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public VentaProductos(VentaProductoId id, Producto producto, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
    }



}
