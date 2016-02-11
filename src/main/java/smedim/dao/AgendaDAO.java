/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.dao;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import smedim.entidade.Agenda;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class AgendaDAO extends GenericDAO<Agenda> {
    
    public List<Agenda> obterPorDia(Date inicio, Date fim) {
        String jpql = "SELECT a FROM Agenda a "
                + "WHERE a.marcado BETWEEN :inicio AND :fim ORDER BY a.marcado";
        TypedQuery<Agenda> query = getEntityManager()
                .createQuery(jpql, Agenda.class)
                .setParameter("inicio", inicio, TemporalType.TIMESTAMP)
                .setParameter("fim", fim, TemporalType.TIMESTAMP);
        return query.getResultList();
    }
}
