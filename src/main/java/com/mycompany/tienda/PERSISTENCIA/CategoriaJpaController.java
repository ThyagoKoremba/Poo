package com.mycompany.tienda.PERSISTENCIA;

import com.mycompany.tienda.LOGICA.Categoria;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class CategoriaJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    // Constructor con EntityManagerFactory
    public CategoriaJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    // Constructor sin parámetros, crea el emf
    public CategoriaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("parcialPOOPU");
    }

    // Método para obtener EntityManager
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // CREATE
    public void create(Categoria categoria) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // EDIT (UPDATE)
    public void edit(Categoria categoria) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(categoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // DESTROY (DELETE)
    public void destroy(int id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, id);
            if (categoria != null) {
                em.remove(categoria);
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
    public Categoria findCategoria(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    // FIND ALL
    public List<Categoria> findCategoriaEntities() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Categoria> cq = em.getCriteriaBuilder().createQuery(Categoria.class);
            cq.select(cq.from(Categoria.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
