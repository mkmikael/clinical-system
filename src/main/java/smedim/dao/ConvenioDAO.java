/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.dao;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import smedim.entidade.Convenio;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ConvenioDAO extends GenericDAO<Convenio> {
    
    public List<Convenio> obterTodos() {
        String jpql = "SELECT c FROM Convenio c ORDER BY c.nome";
        TypedQuery<Convenio> query = getEntityManager()
                .createQuery(jpql, Convenio.class);
        return query.getResultList();
    }

    public List<Convenio> listWithSC() {
        TypedQuery<Convenio> query = getEntityManager()
                .createNamedQuery("Convenio.findAllWithSC", Convenio.class);
        return query.getResultList();
    }
    
}
