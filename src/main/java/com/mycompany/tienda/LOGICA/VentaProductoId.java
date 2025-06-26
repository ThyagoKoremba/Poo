package com.mycompany.tienda.LOGICA;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class VentaProductoId implements Serializable {

    private Long ventaId;
    private Long productoId;

    public VentaProductoId() {
    }

    public VentaProductoId(Long ventaId, Long productoId) {
        this.ventaId = ventaId;
        this.productoId = productoId;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ventaId, productoId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof VentaProductoId)) return false;
        VentaProductoId other = (VentaProductoId) obj;
        return Objects.equals(this.ventaId, other.ventaId)
            && Objects.equals(this.productoId, other.productoId);
    }
}
    