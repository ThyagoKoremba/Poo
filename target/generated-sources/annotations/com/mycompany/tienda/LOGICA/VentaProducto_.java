package com.mycompany.tienda.LOGICA;

import com.mycompany.tienda.LOGICA.Producto;
import com.mycompany.tienda.LOGICA.Venta;
import com.mycompany.tienda.LOGICA.VentaProductoId;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-25T21:16:27", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(VentaProducto.class)
public class VentaProducto_ { 

    public static volatile SingularAttribute<VentaProducto, Venta> venta;
    public static volatile SingularAttribute<VentaProducto, VentaProductoId> id;
    public static volatile SingularAttribute<VentaProducto, Producto> producto;
    public static volatile SingularAttribute<VentaProducto, Integer> cantidad;

}