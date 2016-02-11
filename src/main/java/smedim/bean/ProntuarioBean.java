/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import java.io.File;
import smedim.util.BeanUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Prontuario;
import smedim.entidade.SubProntuario;
import smedim.rn.ProntuarioRN;
import smedim.rn.SubProntuarioRN;
import smedim.rn.relatorio.GerarRelatorio;

/**
 *
 * @author Mikael Lima
 */
@Named
@SessionScoped
public class ProntuarioBean implements Serializable {

    @Inject
    private ProntuarioRN rn;
    @Inject
    private SubProntuarioRN rnS;

    private SubProntuario subProntuario;
    private Prontuario prontuario;

    private List<SubProntuario> subProntuarios;
    private List<Prontuario> prontuarios;

    public void salvar() {
        if (rn.salvar(prontuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O prontuário foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o prontuário.");
        }
    }

    public void deletar() {
        if (rn.deletar(prontuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O prontuário foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel remover o prontuário.");
        }
    }

    public void addProntuario() {
        if (rnS.salvar(subProntuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O prontuário foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o prontuário.");
        }
    }

    public void removerProntuario() {
        if (rnS.deletar(subProntuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O prontuário foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel remover o prontuário.");
        }
    }

    public void exportarParaPDF() {
        Map<String, Object> param = new HashMap<>();
        param.put("CLIENTE_NOME", prontuario.getCliente().getNome());
        param.put("ENDERECO", prontuario.getCliente().getEndereco());
        param.put("NASCIMENTO", prontuario.getCliente().getNascimento());
        param.put("CONVENIO", prontuario.getCliente().getConvenio().getNome());
        param.put("ANT_PESSOAIS", prontuario.getAntecedentesPessoais());
        param.put("ANT_FAMILIARES", prontuario.getAntecedentesFamiliares());
        param.put("IMAGEM", GerarRelatorio.getPath() + File.separator + "imagens" + File.separator);
        List<SubProntuario> lista = new ArrayList<>();
        lista.add(subProntuario);
        GerarRelatorio.exportarParaPDF("prontuario", param, lista);
    }

    public void inserir() {
        subProntuario = new SubProntuario();
        subProntuario.setAtendimento(new Date());
        subProntuario.setProntuario(prontuario);
    }

    public List<Prontuario> autoComplete(String busca) {
        return rn.autoComplete(busca);
    }

    public List<Prontuario> getProntuarios() {
        prontuarios = rn.obterTodos();
        return prontuarios;
    }

    public List<SubProntuario> getSubProntuarios() {
        subProntuarios = rnS.obterPorProntuario(prontuario);
        return subProntuarios;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    public SubProntuario getSubProntuario() {
        return subProntuario;
    }

    public void setSubProntuario(SubProntuario subProntuario) {
        this.subProntuario = subProntuario;
    }

}
