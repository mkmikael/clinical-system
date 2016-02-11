/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.rn;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import smedim.dao.SubProntuarioDAO;
import smedim.entidade.Prontuario;
import smedim.entidade.SubProntuario;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class SubProntuarioRN implements Serializable {

    @Inject
    private SubProntuarioDAO dao;
    
    public boolean salvar(SubProntuario subProntuario) {
        if (subProntuario.getId() == null || subProntuario.getId() == 0) {
            return dao.criar(subProntuario);
        } else {
            return dao.alterar(subProntuario);
        }
    }

    public boolean deletar(SubProntuario subProntuario) {
        return dao.remover(subProntuario);
    }
    
    public SubProntuario obterPorId(Integer id) {
        return dao.obterPorId(SubProntuario.class, id);
    }
    
    public List<SubProntuario> obterTodos() {
        return dao.obterTodos(SubProntuario.class);
    }

    public List<SubProntuario> obterPorProntuario(Prontuario prontuario) {
        if (prontuario == null) {
            return Collections.EMPTY_LIST;
        }
        return dao.obterPorProntuario(prontuario);
    }

    public boolean alterar(SubProntuario o) {
        return dao.alterar(o);
    }
    
}
