/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import smedim.dao.ClienteDAO;
import smedim.entidade.Cliente;
import smedim.entidade.Prontuario;
import smedim.entidade.Usuario;
import smedim.util.BeanUtil;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ClienteRN implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(ClienteRN.class.getName());
    @Inject
    private ClienteDAO dao;
    @Inject
    private ProntuarioRN rnP;

    public boolean salvar(Cliente cliente) {
        if (cliente.getId() == null || cliente.getId() == 0) {
            dao.criar(cliente);
            Prontuario p = new Prontuario();
            p.setAntecedentesFamiliares("sem registros");
            p.setAntecedentesPessoais("sem registros");
            p.setCliente(cliente);
            return rnP.salvar(p);
        } else {
            return dao.alterar(cliente);
        }
    }

    public boolean deletar(Cliente cliente) {
        return dao.remover(cliente);
    }

    public List<Cliente> autoComplete(String busca) {
        if (busca.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            Usuario user = null;
            try {
                user = (Usuario) BeanUtil.lerDaSessao("usuarioLogado");
            } catch (Exception e) {
                LOG.severe("Erro ao obter usuario da sessão ou erro de cast");
                BeanUtil.mensagem(FacesMessage.SEVERITY_FATAL, "Não foi possível converter o usuário!");
            }
            List<Cliente> lista = obterTodos();
            List<Cliente> filtrado = new ArrayList<>();

            for (Cliente cliente : lista) {
                boolean condition = cliente.getNome().toUpperCase().contains(busca.toUpperCase());
                
                if (user != null && user.getMedico()!= null && cliente.getMedico() != null)
                    condition &= cliente.getMedico().equals(user.getMedico());
                
                if (condition)
                    filtrado.add(cliente);
            }

            return filtrado;
        }
    }

    public Cliente obterPorId(Integer id) {
        return dao.obterPorId(Cliente.class, id);
    }

    public List<Cliente> obterTodos() {
        Object o = BeanUtil.lerDaSessao("usuarioLogado");
        Usuario u = null;
        if (o != null && o instanceof Usuario)
            u = (Usuario) BeanUtil.lerDaSessao("usuarioLogado");
        ArrayList<Cliente> clientes = new ArrayList<>();
        if (u.getPerfil() == 'S' || u.getPerfil() == 'A') {
            clientes.addAll(dao.obterTodos());
        } else {
            for (Cliente cliente : dao.obterTodos()) {
                if ( u != null && cliente.getMedico() != null && u.getMedico().equals( cliente.getMedico() ) )
                    clientes.add(cliente);
            }
        }
        return clientes;
    }
}
