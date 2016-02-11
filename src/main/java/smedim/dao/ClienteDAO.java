/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.dao;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import smedim.entidade.Cliente;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ClienteDAO extends GenericDAO<Cliente> {
    
    public List<Cliente> obterTodos() {
        String jpql = "SELECT c FROM Cliente c ORDER BY c.nome";
        TypedQuery<Cliente> query = getEntityManager()
                .createQuery(jpql, Cliente.class);
        return query.getResultList();
    }
    
}
