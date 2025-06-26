package com.mycompany.tienda.LOGICA;

import com.mycompany.tienda.LOGICA.Producto;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-25T21:49:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile SingularAttribute<Categoria, String> descripcion;
    public static volatile SingularAttribute<Categoria, Integer> id;
    public static volatile ListAttribute<Categoria, Producto> productos;

}