/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.bean;

import smedim.util.BeanUtil;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Servico;
import smedim.rn.ServicoRN;

/**
 *
 * @author Mikael Lima
 */
@Named
@RequestScoped
public class ServicoBean implements Serializable {

    @Inject
    private ServicoRN rn;
    private Servico servico = new Servico();
    private List<Servico> servicos;

    public void salvar() {
        if (rn.salvar(servico)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O serviço foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o serviço.");
        }
    }
    
    public void deletar() {
        if (rn.deletar(servico)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O serviço foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel remover o serviço.");
        }
    }
    
    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<Servico> getServicos() {
        servicos = rn.obterTodos();
        return servicos;
    }
    
}
