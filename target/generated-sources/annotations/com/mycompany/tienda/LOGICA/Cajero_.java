package com.mycompany.tienda.LOGICA;

import com.mycompany.tienda.LOGICA.Venta;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-25T21:49:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Cajero.class)
public class Cajero_ { 

    public static volatile ListAttribute<Cajero, Venta> ventas;
    public static volatile SingularAttribute<Cajero, Integer> id;
    public static volatile SingularAttribute<Cajero, String> user;
    public static volatile SingularAttribute<Cajero, String> contraseña;

}