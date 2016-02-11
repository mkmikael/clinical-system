/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import smedim.dao.AgendaDAO;
import smedim.entidade.Agenda;
import smedim.entidade.Convenio;
import smedim.entidade.ServicoConvenio;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class AgendaRN implements Serializable {

    @Inject
    private AgendaDAO dao;
    @Inject
    private ServicoConvenioRN rnS;

    public boolean salvar(Agenda cliente) {
        if (cliente.getId() == null || cliente.getId() == 0) {
            return dao.criar(cliente);
        } else {
            return dao.alterar(cliente);
        }
    }

    public boolean deletar(Agenda cliente) {
        return dao.remover(cliente);
    }

    public Agenda obterPorId(Integer id) {
        return dao.obterPorId(Agenda.class, id);
    }

    public List<Agenda> obterTodos() {
        return dao.obterTodos(Agenda.class);
    }

    public List<ServicoConvenio> obterPorConvenio(Convenio convenio) {
        if (convenio == null) {
            return Collections.EMPTY_LIST;
        }
        return rnS.obterPorConvenio(convenio);
    }

    public List<Agenda> obterPorDia(Date data) {
        if (data == null) {
            return Collections.EMPTY_LIST;
        }
        Date inicio = (Date) data.clone();
        Date fim = (Date) data.clone();
        inicio.setHours(0);
        inicio.setMinutes(0);
        inicio.setSeconds(0);
        fim.setHours(23);
        fim.setMinutes(59);
        fim.setSeconds(59);
        
        return dao.obterPorDia(inicio, fim);
    }

}
