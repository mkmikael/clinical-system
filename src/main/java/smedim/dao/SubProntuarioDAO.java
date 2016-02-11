/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.dao;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import smedim.entidade.Prontuario;
import smedim.entidade.SubProntuario;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class SubProntuarioDAO extends GenericDAO<SubProntuario> {
    
    public List<SubProntuario> obterPorProntuario(Prontuario prontuario) {
        String jpql = "SELECT sp FROM SubProntuario sp "
                + "WHERE sp.prontuario = :prontuario "
                + "ORDER BY sp.atendimento DESC";
        TypedQuery<SubProntuario> query = getEntityManager()
                .createQuery(jpql, SubProntuario.class)
                .setParameter("prontuario", prontuario);
        return query.getResultList();
    }
}
