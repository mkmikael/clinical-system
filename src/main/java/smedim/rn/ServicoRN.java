/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.rn;

import java.io.Serializable;
import java.util.List;
import smedim.dao.ServicoDAO;
import smedim.entidade.Servico;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ServicoRN implements Serializable {

    @Inject
    private ServicoDAO dao;
    
    public boolean salvar(Servico servico) {
        if (servico.getId() == null || servico.getId() == 0) {
            return dao.criar(servico);
        } else {
            return dao.alterar(servico);
        }
    }
    
    public boolean deletar(Servico servico) {
        return dao.remover(servico);
    }
    
    public Servico obterPorId(Integer id) {
        return dao.obterPorId(Servico.class, id);
    }
    
    public List<Servico> obterTodos() {
        return dao.obterTodos();
    }
}
