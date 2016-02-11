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

import smedim.entidade.Cliente;
import smedim.entidade.Medico;
import smedim.rn.ClienteRN;
import smedim.rn.MedicoRN;

/**
 *
 * @author Mikael Lima
 */
@Named
@RequestScoped
public class ClienteBean implements Serializable {

    @Inject
    private ClienteRN rn;
    @Inject
    private MedicoRN rnMedico;
    private Cliente cliente = new Cliente();
    private List<Cliente> clientes;
    private List<Medico> medicos;

    public void salvar() {
        if (rn.salvar(cliente)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O cliente foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o cliente.");
        }
    }

    public void deletar() {
        if (rn.deletar(cliente)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O cliente foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! O não foi possivel remover o cliente.");
        }
    }

    public List<Cliente> autoComplete(String busca) {
        return rn.autoComplete(busca);
    }

    public List<Cliente> getClientes() {
        clientes = rn.obterTodos();
        return clientes;
    }

    public List<Medico> getMedicos() {
        medicos = rnMedico.obterTodos();
        return medicos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
