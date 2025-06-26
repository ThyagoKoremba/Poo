package com.mycompany.tienda.LOGICA;

import com.mycompany.tienda.LOGICA.Categoria;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-25T21:41:13", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, Double> precio;
    public static volatile SingularAttribute<Producto, Categoria> categoria;
    public static volatile SingularAttribute<Producto, Long> Id;
    public static volatile SingularAttribute<Producto, Integer> stock;
    public static volatile SingularAttribute<Producto, String> nombre;

}