/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Convenio;
import smedim.entidade.Faturamento;
import smedim.entidade.Medico;
import smedim.rn.ConvenioRN;
import smedim.rn.FaturamentoRN;

/**
 *
 * @author Mikael Lima
 */
@Named
@RequestScoped
public class FaturamentoPorConvenioBean implements Serializable {

    @Inject
    private FaturamentoRN rn;
    @Inject
    private ConvenioRN rnC;
    private List<Faturamento> faturamentos;
    private List<Convenio> convenios;
    private Convenio convenio;

    private Medico medico;
    private Integer ano = LocalDate.now().getYear();
    private Integer mes = LocalDate.now().getMonthValue();
    private Double total;
    private Double totalGeral;
    private Long num;
    private Long numGeral;

    public List<Convenio> getConvenios() {
        convenios = rnC.obterTodos();
        return convenios;
    }

    public List<Faturamento> getFaturamentos() {
        faturamentos = rn.obterPorConvenioEPorMes(convenio, mes, ano);
        return faturamentos;
    }

    public List<Faturamento> getFaturamentosByConvenio(Convenio convenio) {
        this.convenio = convenio;
        faturamentos = rn.obterPorConvenioEPorMes(convenio, mes, ano);
        if (medico != null) {
            List<Faturamento> toRemove = new ArrayList<>();
            for (Faturamento f : faturamentos) {
                if (!f.getMedico().equals(medico))
                    toRemove.add(f);
            }
            faturamentos.removeAll(toRemove);
        }
        return faturamentos;
    }

    public Long getNum() {
        //num = rn.obterTotalDeAtendimentosPorConvenioEPorMes(convenio, mes, ano);
        num = 0l;
        for (Faturamento f : faturamentos) {
            num += f.getNumDeAtendimento();
        }
        return num;
    }

    public double getTotal() {
        //total = rn.obterTotalPorConvenioEPorMes(convenio, mes, ano);
        total = 0.0;
        for (Faturamento f : faturamentos) {
            total += (f.getPreco() * f.getNumDeAtendimento());
        }
        return total;
    }

    public Long getNumGeral() {
        numGeral = rn.obterTotalDeAtendimentosPorMes(mes, ano);
        return numGeral;
    }

    public double getTotalGeral() {
        totalGeral = rn.obterTotalPorMes(mes, ano);
        return totalGeral;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
