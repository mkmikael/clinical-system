/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import javafx.beans.value.ChangeListener;
import smedim.dao.ConvenioDAO;
import smedim.dao.GenericDAO;
import smedim.dao.ServicoDAO;
import smedim.entidade.Convenio;
import smedim.entidade.Medico;
import smedim.entidade.Servico;
import smedim.entidade.ServicoConvenio;
import smedim.rn.ConvenioRN;
import smedim.rn.MedicoRN;
import smedim.util.BeanUtil;

/**
 *
 * @author Simeia Lima
 */
@Named
@RequestScoped
public class MedicoBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(MedicoBean.class.getClass().getName());

    @Inject
    private MedicoRN rn;
    @Inject
    private ConvenioDAO convenioDAO;
    @Inject
    private ServicoDAO servicoDAO;
    private List<Medico> medicos;
    private List<Convenio> convenios;
    private List<Servico> servicos;
    private Medico medico = new Medico();
    
    public void salvar() {
        if (rn.salvar(medico)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O médico foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o médico.");
        }
    }
    
    public void deletar() {
        if (rn.deletar(medico)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O médico foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! O não foi possivel remover o médico.");
        }
    }
    
    public List<Medico> getMedicos() {
        medicos = rn.obterTodos();
        return medicos;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Convenio> getConvenios() {
        convenios = convenioDAO.listWithSC();
        return convenios;
    }

    public List<Servico> getServicos() {
        servicos = servicoDAO.findAllWithSC();
        return servicos;
    }

    public void saveListener(AjaxBehaviorEvent event) {
        if (medico != null && medico.getId() != null) {
            MedicoRN rn = new MedicoRN();
            rn.salvar(medico);
            LOG.info("Save medico");
        } else {
            LOG.severe("O attr medico nao foi setado");
        }
    }
}
