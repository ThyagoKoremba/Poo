/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda.PERSISTENCIA;

import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author xthy
 */
public class CategoriaJpaController implements Serializable {
        private EntityManagerFactory emf;

    public CategoriaJpaController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public CategoriaJpaController (){
        this.emf = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("parcialPOOPU");
    }
}
