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
import smedim.entidade.Servico;
import smedim.entidade.ServicoConvenio;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ServicoConvenioDAO extends GenericDAO<ServicoConvenio> {
    
    public List<ServicoConvenio> obterPorConvenio(Convenio convenio) {
        String jpql = "SELECT s FROM ServicoConvenio s WHERE s.convenio = :convenio";
        TypedQuery<ServicoConvenio> query = getEntityManager()
                .createQuery(jpql, ServicoConvenio.class)
                .setParameter("convenio", convenio);
        return query.getResultList();
    }
    
    public List<Servico> obterServicoNaoSelecionadoPorConvenio(Convenio convenio) {
        String jpql = "SELECT s FROM Servico s "
                + "WHERE s.id <> ALL(SELECT sc.servico.id FROM ServicoConvenio sc "
                + "WHERE sc.convenio = :convenio) ORDER BY s.nome ASC";
        TypedQuery<Servico> query = getEntityManager()
                .createQuery(jpql, Servico.class)
                .setParameter("convenio", convenio);
        return query.getResultList();
    }
    
    public List<ServicoConvenio> obterNaoSelecionadoPorDia(Date date) {
        String jpql = "SELECT sc FROM ServicoConvenio sc "
                + "WHERE sc.id <> ALL(SELECT f.servicoConvenio.id FROM Faturamento f "
                + "WHERE f.dataDoFaturamento = :dataDoFaturamento)";
        TypedQuery<ServicoConvenio> query = getEntityManager()
                .createQuery(jpql, ServicoConvenio.class)
                .setParameter("dataDoFaturamento", date, TemporalType.DATE);
        return query.getResultList();
    }
    
}
