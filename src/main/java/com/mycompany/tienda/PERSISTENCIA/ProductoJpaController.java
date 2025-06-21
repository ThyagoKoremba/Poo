/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda.PERSISTENCIA;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author xthy
 */
public class ProductoJpaController {
    private EntityManagerFactory emf;

    public ProductoJpaController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public ProductoJpaController (){
        this.emf = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("parcialPOOPU");
    } 
}
