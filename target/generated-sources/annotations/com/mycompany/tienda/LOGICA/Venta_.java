package com.mycompany.tienda.LOGICA;

import com.mycompany.tienda.LOGICA.Cajero;
import com.mycompany.tienda.LOGICA.VentaProductos;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-25T21:41:13", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, LocalDate> fecha;
    public static volatile SingularAttribute<Venta, Long> id;
    public static volatile ListAttribute<Venta, VentaProductos> productos;
    public static volatile SingularAttribute<Venta, Cajero> cajero;

}