/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import smedim.util.BeanUtil;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Convenio;
import smedim.entidade.Servico;
import smedim.entidade.ServicoConvenio;
import smedim.rn.ServicoConvenioRN;

/**
 *
 * @author Mikael Lima
 */
@Named
@ConversationScoped
public class ServicoConvenioBean implements Serializable {

    @Inject
    private ServicoConvenioRN rn;
    private List<ServicoConvenio> servicoConvenios;
    private List<ServicoConvenio> servicoConveniosByConvenio;
    private List<Servico> servicos;
    private List<ServicoConvenio> obterTodos;
    private ServicoConvenio servicoConvenio = new ServicoConvenio();
    private Convenio convenio;

    public void salvar() {
        if (rn.salvar(servicoConvenio)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! A vinculação foi salva.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel vincular.");
        }
    }

    public void deletar() {
        if (rn.deletar(servicoConvenio)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! A vinculação foi removida.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel remover a vinculação.");
        }
        servicoConvenio = new ServicoConvenio();
        servicoConvenio.setConvenio(convenio);
    }

    public void incluir() {
        servicoConvenio = new ServicoConvenio();
        servicoConvenio.setConvenio(convenio);
    }

    public List<ServicoConvenio> getServicoConvenios() {
        servicoConvenios = rn.obterPorConvenio(convenio);
        return servicoConvenios;
    }

    public List<ServicoConvenio> getServicoConveniosByConvenio(Convenio convenio) {
        ServicoConvenioRN rnSC = new ServicoConvenioRN();
        servicoConveniosByConvenio = rnSC.obterPorConvenio(convenio);
        return servicoConveniosByConvenio;
    }

    public List<ServicoConvenio> getObterTodos() {
        obterTodos = rn.obterTodos();
        return obterTodos;
    }

    public List<Servico> getServicos() {
        servicos = rn.obterServicoNaoSelecionadoPorConvenio(convenio);
        return servicos;
    }

    public ServicoConvenio getServicoConvenio() {
        return servicoConvenio;
    }

    public void setServicoConvenio(ServicoConvenio servicoConvenio) {
        this.servicoConvenio = servicoConvenio;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

}
