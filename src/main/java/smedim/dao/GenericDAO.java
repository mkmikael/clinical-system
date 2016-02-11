/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mikael Lima
 * @param <T>
 */
public class GenericDAO<T> implements Serializable {
    private static final Logger LOG = Logger.getLogger(GenericDAO.class.getName());

    @Inject
    private EntityManager em;

    public GenericDAO() {
    }
    
    public boolean criar(T o) {
        EntityTransaction t = null;
        try {
            t = em.getTransaction();
            t.begin();
            em.persist(o);
            t.commit();
            return true;
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            LOG.log(Level.SEVERE, "Erro camada DAO", e);
            return false;
        }
    }
    
    public boolean alterar(T o) {
        EntityTransaction t = null;
        try {
            t = em.getTransaction();
            t.begin();
            em.merge(o);
            t.commit();
            return true;
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            LOG.log(Level.SEVERE, "Erro camada DAO", e);
            return false;
        }
    }
    
    public boolean remover(T o) {
        EntityTransaction t = null;
        try {
            t = em.getTransaction();
            t.begin();
            em.remove(em.merge(o));
            t.commit();
            return true;
        } catch (Exception e) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
            System.out.println("EXCEPTION: " + e);
            return false;
        }
    }
    
    public T obterPorId(Class<T> clazz, Object id) {
        TypedQuery<T> query = em.createNamedQuery(
                clazz.getSimpleName() + ".findById", clazz)
                .setParameter("id", id);
        return query.getSingleResult();
    }
    
    public List<T> obterTodos(Class<T> clazz) {
        TypedQuery<T> query = em.createNamedQuery(
                clazz.getSimpleName() + ".findAll", clazz);
        return query.getResultList();
    }

    public EntityManager getEntityManager() {
        return em;
    }
    
}
