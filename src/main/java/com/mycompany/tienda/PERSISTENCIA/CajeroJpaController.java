package com.mycompany.tienda.PERSISTENCIA;

import com.mycompany.tienda.LOGICA.Cajero;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class CajeroJpaController implements Serializable {

    private EntityManagerFactory emf;

    // Constructor con EntityManagerFactory inyectado
    public CajeroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Constructor por defecto: crea el EMF usando la unidad de persistencia
    public CajeroJpaController() {
        this.emf = Persistence.createEntityManagerFactory("parcialPOOPU");
    }

    // Método para obtener el EntityManager
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para buscar un cajero por user y contraseña
    public Cajero buscarPorUsuarioYPassword(String user, String contraseña) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cajero c WHERE c.user = :user AND c.contraseña = :contraseña",
                    Cajero.class
                )
                .setParameter("user", user)
                .setParameter("contraseña", contraseña)
                .getSingleResult();
        } catch (NoResultException e) {
            return null; // Login incorrecto
        } finally {
            em.close();
        }
    }

    public void crearCajero(Cajero cajero) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cajero);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    public Cajero encontrarCajeroPorId(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cajero.class, id);
        } finally {
            em.close();
        }
    }

    public void eliminarCajero(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Cajero cajero = em.find(Cajero.class, id);
            if (cajero != null) {
                em.remove(cajero);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
