/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.dao;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import smedim.entidade.Servico;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ServicoDAO extends GenericDAO<Servico> {
    
    public List<Servico> obterTodos() {
        String jpql = "SELECT s FROM Servico s ORDER BY s.nome";
        TypedQuery<Servico> query = getEntityManager()
                .createQuery(jpql, Servico.class);
        return query.getResultList();
    }

    public List<Servico> findAllWithSC() {
        TypedQuery<Servico> query = getEntityManager()
                .createNamedQuery("Servico.findAllWithSC", Servico.class);
        return query.getResultList();
    }
    
}
