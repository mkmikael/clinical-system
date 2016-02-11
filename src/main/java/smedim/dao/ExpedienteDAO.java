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
import smedim.entidade.Faturamento;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ExpedienteDAO extends GenericDAO<Faturamento> {

    public List<Faturamento> obterPorDia(Date date) {
        TypedQuery<Faturamento> query = getEntityManager()
                .createQuery("SELECT f FROM Faturamento f "
                        + "WHERE f.dataDoFaturamento = :dataDoFaturamento", Faturamento.class)
                .setParameter("dataDoFaturamento", date, TemporalType.DATE);
        return query.getResultList();
    }

    public Double obterTotalPorDia(Date date) {
        String jpql = "SELECT SUM(f.servicoConvenio.preco * f.numDeAtendimento) "
                + "FROM Faturamento f "
                + "WHERE f.dataDoFaturamento = :dataDoFaturamento";
        TypedQuery<Double> query = getEntityManager()
                .createQuery(jpql, Double.class)
                .setParameter("dataDoFaturamento", date, TemporalType.DATE);
        return query.getSingleResult();
    }

    public Long obterTotalDeAtendimentosPorDia(Date date) {
        String jpql = "SELECT SUM(f.numDeAtendimento) "
                + "FROM Faturamento f "
                + "WHERE f.dataDoFaturamento = :dataDoFaturamento";
        TypedQuery<Long> query = getEntityManager()
                .createQuery(jpql, Long.class)
                .setParameter("dataDoFaturamento", date, TemporalType.DATE);
        return query.getSingleResult();
    }
}
