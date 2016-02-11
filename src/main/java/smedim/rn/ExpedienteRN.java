/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import smedim.dao.ExpedienteDAO;
import smedim.entidade.Faturamento;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ExpedienteRN implements Serializable {

    @Inject
    private ExpedienteDAO dao;

    public boolean salvar(Faturamento o) {
        o.setPreco(o.getServicoConvenio().getPreco());
        if (o.getId() == null || o.getId() == 0) {
            return dao.criar(o);
        } else {
            return dao.alterar(o);
        }
    }

    public boolean deletar(Faturamento o) {
        return dao.remover(o);
    }

    public void addNumAtendimento(Faturamento f) {
        f.setNumDeAtendimento(f.getNumDeAtendimento() + 1);
        salvar(f);
    }

    public void subNumAtendimento(Faturamento f) {
        f.setNumDeAtendimento(f.getNumDeAtendimento() - 1);
        if (f.getNumDeAtendimento() < 0) {
            f.setNumDeAtendimento(0);
        }
        salvar(f);
    }

    public Faturamento obterPorId(Integer id) {
        return dao.obterPorId(Faturamento.class, id);
    }

    public List<Faturamento> obterTodos() {
        return dao.obterTodos(Faturamento.class);
    }

    public List<Faturamento> obterPorDia(Date date) {
        return dao.obterPorDia(date);
    }

    public Double obterTotalPorDia(Date data) {
        return dao.obterTotalPorDia(data);
    }

    public Long obterTotalDeAtendimentosPorDia(Date data) {
        return dao.obterTotalDeAtendimentosPorDia(data);
    }
}
