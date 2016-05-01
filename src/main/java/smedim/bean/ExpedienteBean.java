/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import org.apache.deltaspike.data.api.criteria.Criteria;
import smedim.entidade.Faturamento;
import smedim.entidade.Faturamento_;
import smedim.entidade.Medico;
import smedim.entidade.ServicoConvenio;
import smedim.repository.FaturamentoAbstractRepository;
import smedim.repository.FaturamentoRepository;
import smedim.rn.ExpedienteRN;
import smedim.rn.FaturamentoService;
import smedim.rn.MedicoService;
import smedim.rn.ServicoConvenioRN;
import smedim.rn.relatorio.ExcelExporter;
import smedim.rn.relatorio.GerarRelatorio;
import smedim.util.BeanUtil;
import smedim.util.DAOUtil;

/**
 *
 * @author Mikael Lima
 */
@Named
@SessionScoped
public class ExpedienteBean implements Serializable {

    @Inject
    private ExpedienteRN rn;
    @Inject
    private MedicoService rnM;
    @Inject
    private ServicoConvenioRN rnSC;
    @Inject
    private FaturamentoService faturamentoService;

    private Faturamento faturamento = new Faturamento();
    private List<Faturamento> faturamentos;
    private List<Medico> medicos;
    private List<ServicoConvenio> servicoConvenioNaoSelecionado;
    private Double total;
    private Long totalAtendimento;

    @PostConstruct
    public void init() {
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

    public List<Faturamento> getFaturamentoByMedicoAndData(Medico medico) {
        return null;
    }

    public Integer countFaturamento(List<Faturamento> faturamentos) {
        int count = 0;
        for (Faturamento f : faturamentos)
            count += f.getNumDeAtendimento();
        return count;
    }

    public Double sumFaturamentoByPreco(List<Faturamento> faturamentos) {
        double sum = 0;
        for (Faturamento f : faturamentos)
            sum += f.getPreco();
        return sum;
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

    public void relatorioParticular() {
        faturamentoService.relatorioByDataAndIsParticular(faturamento.getDataDoFaturamento(), true);
    }

    public void relatorioConvenios() {
        faturamentoService.relatorioByDataAndIsParticular(faturamento.getDataDoFaturamento(), false);
    }

}
