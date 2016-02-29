/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import java.util.List;
import smedim.dao.MedicoDAO;
import smedim.entidade.Medico;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Simeia Lima
 */
@RequestScoped
public class MedicoRN {

    @Inject
    private MedicoDAO dao;
     
    public boolean salvar(Medico medico) {
        if (medico.getId() == null || medico.getId() == 0) {
            return dao.criar(medico);
        } else {
            return dao.alterar(medico);
        }
    }
        
    public boolean deletar(Medico medico) {
        return dao.remover(medico);
    }
    
    public Medico obterPorId(Integer id) {
       return dao.obterPorId(Medico.class, id);
    }
    
    public List<Medico> obterTodos() {
        return dao.obterTodos(Medico.class);
    }
}
