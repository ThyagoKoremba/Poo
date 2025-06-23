package com.mycompany.tienda.PERSISTENCIA;

import com.mycompany.tienda.LOGICA.Venta;
import com.mycompany.tienda.LOGICA.VentaProductos;
import com.mycompany.tienda.LOGICA.VentaProductoId;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class VentaJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public VentaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("parcialPOOPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // CREATE
    public void create(Venta venta) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            // Persistimos primero la venta sola
            em.persist(venta);
            em.flush(); // Forzamos la generación del ID

            // Persistimos los productos relacionados
            if (venta.getProductos() != null) {
                for (VentaProductos vp : venta.getProductos()) {
                    if (vp.getId() == null) {
                        vp.setId(new VentaProductoId());
                    }
                    vp.getId().setVentaId(venta.getId());
                    vp.getId().setProductoId(vp.getProducto().getId());
                    vp.setVenta(venta);
                    em.persist(vp);
                }
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // EDIT
    public void edit(Venta venta) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            venta = em.merge(venta);

            // Actualizamos los productos
            if (venta.getProductos() != null) {
                for (VentaProductos vp : venta.getProductos()) {
                    if (vp.getId() == null) {
                        vp.setId(new VentaProductoId());
                    }
                    vp.getId().setVentaId(venta.getId());
                                        vp.getId().setProductoId(vp.getProducto().getId());

                    vp.setVenta(venta);
                    em.merge(vp);
                }
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // DELETE
    public void destroy(Long id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Venta venta = em.find(Venta.class, id);
            if (venta != null) {
                em.remove(venta);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // FIND BY ID
    public Venta findVenta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    // FIND ALL
    public List<Venta> findVentaEntities() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Venta> cq = em.getCriteriaBuilder().createQuery(Venta.class);
            cq.select(cq.from(Venta.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
