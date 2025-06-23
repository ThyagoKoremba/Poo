/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda.LOGICA;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author xthy
 */
@Entity
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "cajero_id")
    private Cajero cajero;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<VentaProductos> productos;

    public Venta() {
    }

    public double getTotal() {
        return productos.stream()
                .mapToDouble(VentaProductos::getSubtotal)
                .sum();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public List<VentaProductos> getProductos() {
        return productos;
    }

    public void setProductos(List<VentaProductos> productos) {
        this.productos = productos;
    }
}
