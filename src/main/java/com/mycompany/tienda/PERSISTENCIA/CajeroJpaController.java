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
public class CajeroJpaController implements Serializable {

    private EntityManagerFactory emf;

    public CajeroJpaController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public CajeroJpaController (){
        this.emf = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("parcialPOOPU");
    }
}

