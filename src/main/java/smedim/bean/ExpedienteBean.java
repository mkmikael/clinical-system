/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Faturamento;
import smedim.entidade.Medico;
import smedim.entidade.ServicoConvenio;
import smedim.rn.ExpedienteRN;
import smedim.rn.MedicoRN;
import smedim.rn.ServicoConvenioRN;
import smedim.rn.relatorio.GerarRelatorio;
import smedim.util.BeanUtil;
import smedim.util.DAOUtil;

/**
 *
 * @author Mikael Lima
 */
@Named
@ConversationScoped
public class ExpedienteBean implements Serializable {

    @Inject
    private ExpedienteRN rn;
    @Inject
    private MedicoRN rnM;
    @Inject
    private ServicoConvenioRN rnSC;

    private Faturamento faturamento = new Faturamento();
    private List<Faturamento> faturamentos;
    private List<Medico> medicos;
    private List<ServicoConvenio> servicoConvenioNaoSelecionado;
    private Double total;
    private Long totalAtendimento;

    public ExpedienteBean() {
        faturamento.setDataDoFaturamento(new Date());
    }

    public void salvar() {
        if (rn.salvar(faturamento)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O faturamento foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o faturamento.");
        }
    }

    public void deletar() {
        if (rn.deletar(faturamento)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O faturamento foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel remover o faturamento.");
        }
    }

    public void exportaParaPDF() {
        Map<String, Object> param = new HashMap<>();
        param.put("SUBREPORT_DIR", GerarRelatorio.getPath() + File.separator);
        param.put("IMAGEM", GerarRelatorio.getPath() + File.separator + "imagens" + File.separator);
        param.put("DATA", faturamento.getDataDoFaturamento());
        param.put("FATURAMENTO_TOTAL", total);
        param.put("NUM_ATENDIMENTOS", totalAtendimento);
        GerarRelatorio.exportarParaPDF("faturamentoDiario", param, DAOUtil.conexao());
    }

    public void inserir() {
        Date date = faturamento.getDataDoFaturamento();
        faturamento = new Faturamento();
        faturamento.setDataDoFaturamento(date);
        faturamento.setNumDeAtendimento(1);
    }

    public void addNumAtendimento() {
        rn.addNumAtendimento(faturamento);
    }

    public void subNumAtendimento() {
        rn.subNumAtendimento(faturamento);
    }

    public List<ServicoConvenio> getServicoConvenioNaoSelecionado() {
        servicoConvenioNaoSelecionado = rnSC.obterNaoSelecionadoPorDia(
                faturamento.getDataDoFaturamento());
        return servicoConvenioNaoSelecionado;
    }


    public List<Faturamento> getFaturamentos() {
        faturamentos = rn.obterPorDia(faturamento.getDataDoFaturamento());
        return faturamentos;
    }

    public Double getTotal() {
        total = rn.obterTotalPorDia(faturamento.getDataDoFaturamento());
        return total;
    }
    public Long getTotalAtendimento() {
        totalAtendimento = rn.obterTotalDeAtendimentosPorDia(faturamento.getDataDoFaturamento());
        return totalAtendimento;
    }

    public Faturamento getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(Faturamento faturamento) {
        this.faturamento = faturamento;
    }

    public List<Medico> getMedicos() {
        medicos = rnM.obterTodos();
        return medicos;
    }

}
