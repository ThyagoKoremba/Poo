
package com.mycompany.tienda.LOGICA;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author xthy
 */
@Entity
public class Cajero implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Basic
    private String user;
    private String contraseña;

public Cajero() {
    }
    
public Cajero(int id, String user, String contraseña) {
    this.id = id;
    this.user = user;
    this.contraseña = contraseña;
}

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }



    
    
}
