/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda.LOGICA;

import java.util.Objects;

/**
 *
 * @author xthy
 */
public class VentaProductoId {

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.ventaId);
        hash = 67 * hash + Objects.hashCode(this.productoId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VentaProductoId other = (VentaProductoId) obj;
        if (!Objects.equals(this.ventaId, other.ventaId)) {
            return false;
        }
        return Objects.equals(this.productoId, other.productoId);
    }
    
    private Long ventaId;
    private Long productoId;
    
    
}
