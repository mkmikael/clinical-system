/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.dao.ConvenioDAO;
import smedim.dao.ServicoDAO;
import smedim.entidade.Convenio;
import smedim.entidade.Medico;
import smedim.entidade.Servico;
import smedim.entidade.Usuario;
import smedim.repository.FaturamentoRepository;
import smedim.rn.MedicoService;
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
    private MedicoService medicoService;
    @Inject
    private ConvenioDAO convenioDAO;
    @Inject
    private ServicoDAO servicoDAO;
    @Inject
    private FaturamentoRepository faturamentoRepository;
    @Inject
    private Logger log;

    private List<Medico> medicos;
    private List<Convenio> convenios;
    private List<Servico> servicos;
    @Inject
    private Medico medico;
    @Inject
    private Usuario usuario;

    @PostConstruct
    public void init() {
    }

    public void salvar() {
        if (medicoService.salvar(medico, usuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O médico foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o médico.");
        }
    }

    public void deletar() {
        if (medicoService.deletar(medico)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O médico foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! O não foi possivel remover o médico.");
        }
    }

    public List<Medico> getMedicos() {
        medicos = medicoService.obterTodos();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void saveListener(AjaxBehaviorEvent event) {
        if (medico != null && medico.getId() != null) {
            medicoService.salvar(medico);
            LOG.info("Save medico");
        } else {
            LOG.severe("O attr medico nao foi setado");
        }
    }

}
