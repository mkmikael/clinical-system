/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.rn;

import java.io.Serializable;
import java.util.List;
import smedim.dao.ConvenioDAO;
import smedim.entidade.Convenio;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ConvenioRN implements Serializable {

    @Inject
    private ConvenioDAO dao;
    
    public boolean salvar(Convenio convenio) {
        if (convenio.getId() == null || convenio.getId() == 0) {
            return dao.criar(convenio);
        } else {
            return dao.alterar(convenio);
        }
    }
        
    public boolean deletar(Convenio convenio) {
        return dao.remover(convenio);
    }
    
    public Convenio obterPorId(Integer id) {
       return dao.obterPorId(Convenio.class, id);
    }
    
    public List<Convenio> obterTodos() {
        return dao.obterTodos();
    }
}
