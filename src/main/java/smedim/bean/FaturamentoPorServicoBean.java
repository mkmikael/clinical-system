/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import smedim.entidade.Faturamento;
import smedim.entidade.Servico;
import smedim.rn.FaturamentoRN;
import smedim.rn.ServicoRN;
import smedim.rn.relatorio.GerarRelatorio;
import smedim.util.DAOUtil;
import smedim.util.Util;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mikael Lima
 */
@Named
@ConversationScoped
public class FaturamentoPorServicoBean implements Serializable {

    @Inject
    private FaturamentoRN rn;
    @Inject
    private ServicoRN rnS;
    private List<Servico> servicos;
    private List<Faturamento> faturamentos;
    private Servico servico;

    private Integer ano = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
    private Integer mes = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH) + 1;
    private Double total;
    private Double totalGeral;
    private Long num;
    private Long numGeral;

    public void exportarParaPDF() {
        Map<String, Object> param = new HashMap<>();
        param.put("ANO", ano);
        param.put("MES", mes);
        param.put("MES_TEXTO", Util.converteData(mes));
        param.put("FATURAMENTO_TOTAL", totalGeral);
        param.put("NUM_ATENDIMENTOS", numGeral);
        param.put("SUBREPORT_DIR", GerarRelatorio.getPath() + File.separator);
        param.put("IMAGEM", GerarRelatorio.getPath() + File.separator + "imagens" + File.separator);
        GerarRelatorio.exportarParaPDF("faturamentoMensal", param, DAOUtil.conexao());
    }
    
    public List<Servico> getServicos() {
        servicos = rnS.obterTodos();
        return servicos;
    }

    public List<Faturamento> getFaturamentos() {
        faturamentos = rn.obterPorServicoEPorMes(servico, mes, ano);
        return faturamentos;
    }

    public Long getNum() {
        num = rn.obterTotalDeAtendimentosPorServicoEPorMes(servico, mes, ano);
        return num;
    }

    public Long getNumGeral() {
        numGeral = rn.obterTotalDeAtendimentosPorMes(mes, ano);
        return numGeral;
    }

    public double getTotal() {
        total = rn.obterTotalPorServicoEPorMes(servico, mes, ano);
        return total;
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

}
