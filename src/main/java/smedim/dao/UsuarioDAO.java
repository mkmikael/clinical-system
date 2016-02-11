/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.dao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import smedim.entidade.Usuario;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class UsuarioDAO extends GenericDAO<Usuario> {

    public Usuario obterPorLoginESenha(String login, String senha) {
        String jpql = "SELECT u FROM Usuario u "
                + "WHERE u.login = :login AND u.senha = :senha";
        TypedQuery<Usuario> query = getEntityManager()
                .createQuery(jpql, Usuario.class)
                .setParameter("login", login)
                .setParameter("senha", senha);
        return query.getSingleResult();
    }
}
