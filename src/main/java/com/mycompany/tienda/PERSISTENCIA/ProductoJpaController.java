package com.mycompany.tienda.PERSISTENCIA;

import com.mycompany.tienda.LOGICA.Categoria;
import com.mycompany.tienda.LOGICA.Producto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class ProductoJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ProductoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("parcialPOOPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // CREATE
    public void create(Producto producto) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // EDIT
    public void edit(Producto producto) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // DELETE
    public void destroy(Long id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                em.remove(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // FIND BY ID
    public Producto findProducto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    // FIND ALL
    public List<Producto> findProductoEntities() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Producto> cq = em.getCriteriaBuilder().createQuery(Producto.class);
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Producto> getProductosPorCategoria(Categoria categoria) {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery(
            "SELECT p FROM Producto p WHERE p.categoria = :categoria", Producto.class)
            .setParameter("categoria", categoria)
            .getResultList();
    } finally {
        em.close();
    }
}
}

