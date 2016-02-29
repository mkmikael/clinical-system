/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.bean;

import org.primefaces.model.LazyDataModel;
import smedim.repository.ClienteRepository;
import smedim.security.CustomAccessDecisionVoter;
import smedim.security.Secured;
import smedim.util.BeanUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import smedim.entidade.Cliente;
import smedim.entidade.Medico;
import smedim.rn.ClienteService;
import smedim.rn.MedicoRN;

/**
 *
 * @author Mikael Lima
 */
@Named
@RequestScoped
@Secured({"A", "S", "M"})
public class ClienteBean implements Serializable {

    @Inject
    private ClienteRepository clienteRepository;
    @Inject
    private ClienteService clienteService;
    @Inject
    private MedicoRN rnMedico;
    @Inject
    private Cliente cliente;
    private List<Cliente> clientes;
    private List<Medico> medicos;
    private LazyDataModel<Cliente> clientesModel;

    @PostConstruct
    public void init() {
        clientesModel = new ClienteLazyTable(clienteService);
    }

    public void salvar() {
        if (clienteService.salvar(cliente)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O cliente foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o cliente.");
        }
    }

    public void deletar() {
        try {
            clienteService.deletar(cliente);
            clientes = clienteService.list();
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O cliente foi removido.");
        } catch (Exception e) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! O não foi possivel remover o cliente.");
        }
    }

    public List<Cliente> autoComplete(String busca) {
        return clienteService.autoComplete(busca);
    }

    public List<Cliente> getClientes() {
        if (clientes == null) {
            clientes = clienteService.list();
        }
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

    public LazyDataModel<Cliente> getClientesModel() {
        return clientesModel;
    }

    public void setClientesModel(LazyDataModel<Cliente> clientesModel) {
        this.clientesModel = clientesModel;
    }
}
