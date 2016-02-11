/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import smedim.dao.FaturamentoDAO;
import smedim.entidade.Convenio;
import smedim.entidade.Faturamento;
import smedim.entidade.Servico;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class FaturamentoRN implements Serializable {

    @Inject
    private FaturamentoDAO dao;

    public boolean salvar(Faturamento o) {
        if (o.getId() == null || o.getId() == 0) {
            return dao.criar(o);
        } else {
            return dao.alterar(o);
        }
    }

    public boolean deletar(Faturamento o) {
        return dao.remover(o);
    }

    public Faturamento obterPorId(Integer id) {
        return dao.obterPorId(Faturamento.class, id);
    }

    public Double obterTotalPorMes(int mes, int ano) {
        Date[] datas = gambiarraParaObterMes(mes, ano);
        Double valor = dao.obterTotalPorMes(datas[0], datas[1]);
        if (valor == null) {
            return 0.0;
        } else {
            return valor;
        }
    }

    public Long obterTotalDeAtendimentosPorMes(int mes, int ano) {
        Date[] datas = gambiarraParaObterMes(mes, ano);
        Long valor = dao.obterTotalDeAtendimentosPorMes(datas[0], datas[1]);
        if (valor == null) {
            return 0L;
        } else {
            return valor;
        }
    }

    public List<Faturamento> obterPorConvenioEPorMes(Convenio convenio, Integer mes, Integer ano) {
        Date[] datas = gambiarraParaObterMes(mes, ano);
        return dao.obterPorConvenioEPorMes(convenio, datas[0], datas[1]);
    }

    public List<Faturamento> obterPorServicoEPorMes(Servico servico, Integer mes, Integer ano) {
        Date[] datas = gambiarraParaObterMes(mes, ano);
        return dao.obterPorServicoEPorMes(servico, datas[0], datas[1]);
    }

    public Double obterTotalPorServicoEPorMes(Servico servico, int mes, int ano) {
        Date[] datas = gambiarraParaObterMes(mes, ano);
        Double valor = dao.obterTotalPorServicoEPorMes(servico, datas[0], datas[1]);
        if (valor == null) {
            return 0.0;
        } else {
            return valor;
        }
    }

    public Double obterTotalPorConvenioEPorMes(Convenio convenio, int mes, int ano) {
        Date[] datas = gambiarraParaObterMes(mes, ano);
        Double valor = dao.obterTotalPorConvenioEPorMes(convenio, datas[0], datas[1]);
        if (valor == null) {
            return 0.0;
        } else {
            return valor;
        }
    }

    public Long obterTotalDeAtendimentosPorServicoEPorMes(Servico servico, int mes, int ano) {
        Date[] datas = gambiarraParaObterMes(mes, ano);
        Long valor = dao.obterTotalDeAtendimentosPorServicoEPorMes(servico, datas[0], datas[1]);
        if (valor == null) {
            return 0L;
        } else {
            return valor;
        }
    }

    public Long obterTotalDeAtendimentosPorConvenioEPorMes(Convenio convenio, int mes, int ano) {
        Date[] datas = gambiarraParaObterMes(mes, ano);
        Long valor = dao.obterTotalDeAtendimentosPorConvenioEPorMes(convenio, datas[0], datas[1]);
        if (valor == null) {
            return 0L;
        } else {
            return valor;
        }
    }

    private Date[] gambiarraParaObterMes(int mes, int ano) {
        Date inicio = new Date();
        inicio.setDate(1);
        inicio.setMonth(mes - 1);
        inicio.setYear(ano - 1900);
        Date fim = (Date) inicio.clone();
        fim.setMonth(mes);
        Date datas[] = {inicio, fim};
        return datas;
    }
}
