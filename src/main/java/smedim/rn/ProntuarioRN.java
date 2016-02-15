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
import smedim.dao.GenericDAO;
import smedim.dao.ProntuarioDAO;
import smedim.entidade.Cliente;
import smedim.entidade.Prontuario;
import smedim.entidade.Usuario;
import smedim.util.BeanUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ProntuarioRN implements Serializable {

    @Inject
    private ProntuarioDAO dao;

    public boolean salvar(Prontuario prontuario) {
        if (prontuario.getId() == null || prontuario.getId() == 0) {
            return dao.criar(prontuario);
        } else {
            return dao.alterar(prontuario);
        }
    }

    public boolean deletar(Prontuario prontuario) {
        return dao.remover(prontuario);
    }

    public List<Prontuario> autoComplete(String busca) {
        if (busca.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            List<Prontuario> lista = obterTodos();
            List<Prontuario> filtrado = new ArrayList<>();
            Object o = BeanUtil.lerDaSessao("usuarioLogado");
            Usuario u = null;
            if (o != null && o instanceof Usuario)
                u = (Usuario) BeanUtil.lerDaSessao("usuarioLogado");
            for (Prontuario prontuario : lista) {
                Cliente cliente = prontuario.getCliente();
                if (cliente.getNome().toUpperCase().contains(busca.toUpperCase())
                        && u != null && cliente.getMedico() != null && u.getMedico().equals(cliente.getMedico())) {
                    filtrado.add(prontuario);
                }
            }

            return filtrado;
        }
    }

    public Prontuario obterPorId(Integer id) {
        return dao.obterPorId(Prontuario.class, id);
    }

    public List<Prontuario> obterTodos() {
        return dao.obterTodos(Prontuario.class);
    }

}
