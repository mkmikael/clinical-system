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
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.data.api.criteria.Criteria;
import smedim.entidade.Cliente;
import smedim.entidade.Cliente_;
import smedim.entidade.Medico;
import smedim.entidade.Usuario;
import smedim.repository.ClienteRepository;
import smedim.rn.MedicoRN;
import smedim.rn.UsuarioRN;
import smedim.util.BeanUtil;

/**
 *
 * @author Mikael Lima
 */
@Named
@RequestScoped
public class UsuarioBean implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(UsuarioBean.class.getName());
    @Inject
    private UsuarioRN rn;
    @Inject
    private MedicoRN rnM;
    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;
    private List<Medico> medicos;
    private boolean medico;
    private boolean recepecao;
    private boolean adm;
    @Inject
    private ClienteRepository clienteRepository;

    public void salvar() {
        if (rn.salvar(usuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O usuário foi salvo.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel salvar o usuário.");
        }
    }
    
    public void deletar() {
        if (rn.deletar(usuario)) {
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO, "Sucesso! O usuário foi removido.");
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Erro! Não foi possivel remover o usuário.");
        }
    }
    
    public List<Usuario> getUsuarios() {
        usuarios = rn.obterTodos();
        return usuarios;
    }
    
    public String autenticar() {
        Criteria<Cliente, Cliente> criteria = clienteRepository.criteria();
        criteria.eq(Cliente_.nome, "");
        LOG.info(clienteRepository.findAll(0, 10).toString());
        usuario = rn.autenticar(usuario);
        if (usuario != null) {
            BeanUtil.colocarNaSessao("usuarioLogado", usuario);
            BeanUtil.mensagem(FacesMessage.SEVERITY_INFO,
                    "Seja Bem Vindo " + usuario.getNome() + "!");
            
            return "/privado/index.xhtml";
        } else {
            BeanUtil.mensagem(FacesMessage.SEVERITY_ERROR, "Erro! Login ou Senha inválidos.");
            return null;
        }
    }
    
    public String deslogar() {
        BeanUtil.removerDaSessao("usuarioLogado");
        return "login";
    }

    public boolean isMedico() {
        medico = BeanUtil.isMedico();
        return medico;
    }

    public boolean isRecepecao() {
        recepecao = BeanUtil.isRecepcao();
        return recepecao;
    }

    public boolean isAdm() {
        adm = BeanUtil.isAdm();
        return adm;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Medico> getMedicos() {
        if (medicos == null) {
            medicos = rnM.obterTodos();
        }
        return medicos;
    }
    
}
