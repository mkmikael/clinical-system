/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Agenda;
import smedim.entidade.ServicoConvenio;
import smedim.rn.AgendaRN;
import smedim.util.BeanUtil;

/**
 *
 * @author Mikael Lima
 */
@Named
@ConversationScoped
public class AgendaBean implements Serializable {

    @Inject
    private AgendaRN rn;
    private Agenda agenda = new Agenda();
    private Date date = new Date();
    private List<Agenda> agendas;
    private List<String> convenios;
    private List<ServicoConvenio> servicoConvenios;

    public void salvar() {
        if (rn.salvar(agenda)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O cliente foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o cliente.");
        }
    }

    public void deletar() {
        if (rn.deletar(agenda)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O cliente foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! O não foi possivel remover o cliente.");
        }
    }

    public void incluir() {
        agenda = new Agenda();
    }

    public List<ServicoConvenio> getServicoConvenios() {
        servicoConvenios = rn.obterPorConvenio(agenda.getConvenio());
        return servicoConvenios;
    }

    public List<Agenda> getAgendas() {
        agendas = rn.obterPorDia(date);
        return agendas;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
