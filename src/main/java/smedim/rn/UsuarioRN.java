/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import java.io.Serializable;
import java.util.List;
import smedim.dao.UsuarioDAO;
import smedim.entidade.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class UsuarioRN implements Serializable {

    @Inject
    private UsuarioDAO dao;

    public boolean salvar(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId() == 0) {
            return dao.criar(usuario);
        } else {
            return dao.alterar(usuario);
        }
    }

    public boolean deletar(Usuario usuario) {
        return dao.remover(usuario);
    }

    public Usuario obterPorId(Integer id) {
        return dao.obterPorId(Usuario.class, id);
    }

    public List<Usuario> obterTodos() {
        return dao.obterTodos(Usuario.class);
    }

    public Usuario autenticar(Usuario usuario) {
        try {
            return dao.obterPorLoginESenha(usuario.getLogin(), usuario.getSenha());
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            return null;
        }
    }
}
