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
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Convenio;
import smedim.rn.ConvenioRN;

/**
 *
 * @author Mikael Lima
 */
@Named
@RequestScoped
public class ConvenioBean implements Serializable {

    @Inject
    private ConvenioRN rn;
    private List<Convenio> convenios;
    private Convenio convenio = new Convenio();
    
    public void salvar() {
        if (rn.salvar(convenio)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O convenio foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o convenio.");
        }
    }
    
    public void deletar() {
        if (rn.deletar(convenio)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O convenio foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! O não foi possivel remover o convenio.");
        }
    }
    
    public List<Convenio> getConvenios() {
        convenios = rn.obterTodos();
        return convenios;
    }
    
    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
    
}
