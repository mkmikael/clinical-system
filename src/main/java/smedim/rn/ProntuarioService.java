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

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import smedim.dao.ProntuarioDAO;
import smedim.entidade.Cliente;
import smedim.entidade.Prontuario;
import smedim.entidade.Usuario;
import smedim.repository.ProntuarioRepository;
import smedim.util.BeanUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Mikael Lima
 */
@ApplicationScoped
public class ProntuarioService implements Serializable {

    @Inject
    private ProntuarioDAO dao;
    @Inject
    private ProntuarioRepository prontuarioRepository;

    @Transactional
    public boolean salvar(Prontuario prontuario) {
        return prontuarioRepository.save(prontuario) != null;
    }

    @Transactional
    public boolean deletar(Prontuario prontuario) {
        try {
            prontuarioRepository.remove(prontuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Prontuario> autoComplete(String busca) {
        if (busca.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            Usuario u = BeanUtil.getUsuarioLogado();
            if (u != null) {
                if (u.getMedico() == null) {
                    BeanUtil.mensagemError("Código 01", "O usuário não possui um perfil de médico.");
                    return Collections.EMPTY_LIST;
                } else
                    return prontuarioRepository.findByNomeILikeAndMedico("%" + busca.toLowerCase() + "%", u.getMedico(), 20);
            } else {
                BeanUtil.mensagemFatal("Código 00", "Não foi possível obter o usuário logado.");
            }
            return Collections.EMPTY_LIST;
        }
    }

    public Prontuario obterPorId(Integer id) {
        return dao.obterPorId(Prontuario.class, id);
    }

    public List<Prontuario> obterTodos() {
        return dao.obterTodos(Prontuario.class);
    }

}
