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
import smedim.dao.ServicoConvenioDAO;
import smedim.entidade.Convenio;
import smedim.entidade.Servico;
import smedim.entidade.ServicoConvenio;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@RequestScoped
public class ServicoConvenioRN implements Serializable {

    @Inject
    private ServicoConvenioDAO dao;

    public boolean salvar(ServicoConvenio servicoConvenio) {
        if (servicoConvenio.getId() == null || servicoConvenio.getId() == 0) {
            return dao.criar(servicoConvenio);
        } else {
            return dao.alterar(servicoConvenio);
        }
    }

    public boolean deletar(ServicoConvenio servicoConvenio) {
        return dao.remover(servicoConvenio);
    }

    public ServicoConvenio obterPorId(Integer id) {
        return dao.obterPorId(ServicoConvenio.class, id);
    }

    public List<ServicoConvenio> obterTodos() {
        return dao.obterTodos(ServicoConvenio.class);
    }

    public List<ServicoConvenio> obterPorConvenio(Convenio convenio) {
        if (convenio == null) {
            return Collections.EMPTY_LIST;
        }
        return dao.obterPorConvenio(convenio);
    }

    public List<Servico> obterServicoNaoSelecionadoPorConvenio(Convenio convenio) {
        if (convenio == null) {
            return Collections.EMPTY_LIST;
        }
        return dao.obterServicoNaoSelecionadoPorConvenio(convenio);
    }

    public List<ServicoConvenio> obterNaoSelecionadoPorDia(Date date) {
        if (date == null) {
            return Collections.EMPTY_LIST;
        } else {
            return dao.obterNaoSelecionadoPorDia(date);
        }
    }

}
