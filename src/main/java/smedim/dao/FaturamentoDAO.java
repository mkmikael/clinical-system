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
import smedim.entidade.Convenio;
import smedim.entidade.Faturamento;
import smedim.entidade.Servico;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class FaturamentoDAO extends GenericDAO<Faturamento> {

    public List<Faturamento> obterTodos() {
        String jpql = "SELECT f FROM Faturamento f "
                + "ORDER BY f.servicoConvenio.servico.nome, "
                + "f.servicoConvenio.convenio.nome";
        TypedQuery<Faturamento> query = getEntityManager()
                .createQuery(jpql, Faturamento.class);
        return query.getResultList();
    }

    public Double obterTotalPorMes(Date inicio, Date fim) {
        String jpql = "SELECT SUM(f.servicoConvenio.preco * f.numDeAtendimento) "
                + "FROM Faturamento f "
                + "WHERE f.dataDoFaturamento >= :inicio "
                + "AND f.dataDoFaturamento < :fim";
        TypedQuery<Double> query = getEntityManager()
                .createQuery(jpql, Double.class)
                .setParameter("inicio", inicio, TemporalType.DATE)
                .setParameter("fim", fim, TemporalType.DATE);
        return query.getSingleResult();
    }

    public List<Faturamento> obterPorConvenioEPorMes(Convenio convenio, Date inicio, Date fim) {
        String jpql = "SELECT f FROM Faturamento f "
                + "WHERE f.servicoConvenio.convenio = :convenio "
                + "AND f.dataDoFaturamento >= :inicio AND f.dataDoFaturamento < :fim";
        TypedQuery<Faturamento> query = getEntityManager()
                .createQuery(jpql, Faturamento.class)
                .setParameter("convenio", convenio)
                .setParameter("inicio", inicio, TemporalType.DATE)
                .setParameter("fim", fim, TemporalType.DATE);
        return query.getResultList();
    }

    public List<Faturamento> obterPorServicoEPorMes(Servico servico, Date inicio, Date fim) {
        String jpql = "SELECT f FROM Faturamento f "
                + "WHERE f.servicoConvenio.servico = :servico "
                + "AND f.dataDoFaturamento >= :inicio AND f.dataDoFaturamento < :fim";
        TypedQuery<Faturamento> query = getEntityManager()
                .createQuery(jpql, Faturamento.class)
                .setParameter("servico", servico)
                .setParameter("inicio", inicio, TemporalType.DATE)
                .setParameter("fim", fim, TemporalType.DATE);
        return query.getResultList();
    }

    public Double obterTotalPorServicoEPorMes(Servico servico, Date inicio, Date fim) {
        String jpql = "SELECT SUM(f.servicoConvenio.preco * f.numDeAtendimento) "
                + "FROM Faturamento f "
                + "WHERE f.dataDoFaturamento >= :inicio "
                + "AND f.dataDoFaturamento < :fim "
                + "AND f.servicoConvenio.servico = :servico";
        TypedQuery<Double> query = getEntityManager()
                .createQuery(jpql, Double.class)
                .setParameter("inicio", inicio, TemporalType.DATE)
                .setParameter("fim", fim, TemporalType.DATE)
                .setParameter("servico", servico);
        return query.getSingleResult();
    }

    public Double obterTotalPorConvenioEPorMes(Convenio convenio, Date inicio, Date fim) {
        String jpql = "SELECT SUM(f.servicoConvenio.preco * f.numDeAtendimento) "
                + "FROM Faturamento f "
                + "WHERE f.dataDoFaturamento >= :inicio "
                + "AND f.dataDoFaturamento < :fim "
                + "AND f.servicoConvenio.convenio = :convenio";
        TypedQuery<Double> query = getEntityManager()
                .createQuery(jpql, Double.class)
                .setParameter("inicio", inicio, TemporalType.DATE)
                .setParameter("fim", fim, TemporalType.DATE)
                .setParameter("convenio", convenio);
        return query.getSingleResult();
    }

    public Long obterTotalDeAtendimentosPorMes(Date inicio, Date fim) {
        String jpql = "SELECT SUM(f.numDeAtendimento) "
                + "FROM Faturamento f "
                + "WHERE f.dataDoFaturamento >= :inicio AND "
                + "f.dataDoFaturamento < :fim";
        TypedQuery<Long> query = getEntityManager()
                .createQuery(jpql, Long.class)
                .setParameter("inicio", inicio, TemporalType.DATE)
                .setParameter("fim", fim);
        return query.getSingleResult();
    }

    public Long obterTotalDeAtendimentosPorServicoEPorMes(Servico servico, Date inicio, Date fim) {
        String jpql = "SELECT SUM(f.numDeAtendimento) "
                + "FROM Faturamento f "
                + "WHERE f.dataDoFaturamento >= :inicio "
                + "AND f.dataDoFaturamento < :fim "
                + "AND f.servicoConvenio.servico = :servico";
        TypedQuery<Long> query = getEntityManager()
                .createQuery(jpql, Long.class)
                .setParameter("inicio", inicio, TemporalType.DATE)
                .setParameter("fim", fim)
                .setParameter("servico", servico);
        return query.getSingleResult();
    }

    public Long obterTotalDeAtendimentosPorConvenioEPorMes(Convenio convenio, Date inicio, Date fim) {
        String jpql = "SELECT SUM(f.numDeAtendimento) "
                + "FROM Faturamento f "
                + "WHERE f.dataDoFaturamento >= :inicio "
                + "AND f.dataDoFaturamento < :fim "
                + "AND f.servicoConvenio.convenio = :convenio";
        TypedQuery<Long> query = getEntityManager()
                .createQuery(jpql, Long.class)
                .setParameter("inicio", inicio, TemporalType.DATE)
                .setParameter("fim", fim)
                .setParameter("convenio", convenio);
        return query.getSingleResult();
    }

}
