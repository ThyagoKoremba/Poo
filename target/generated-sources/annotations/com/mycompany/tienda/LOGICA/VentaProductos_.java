package com.mycompany.tienda.LOGICA;

import com.mycompany.tienda.LOGICA.Producto;
import com.mycompany.tienda.LOGICA.Venta;
import com.mycompany.tienda.LOGICA.VentaProductoId;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-23T11:30:17", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(VentaProductos.class)
public class VentaProductos_ { 

    public static volatile SingularAttribute<VentaProductos, Venta> venta;
    public static volatile SingularAttribute<VentaProductos, VentaProductoId> id;
    public static volatile SingularAttribute<VentaProductos, Producto> producto;
    public static volatile SingularAttribute<VentaProductos, Integer> cantidad;

}