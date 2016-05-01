/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smedim.rn;

import java.io.Serializable;
import java.util.List;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import smedim.dao.MedicoDAO;
import smedim.entidade.Medico;
import smedim.entidade.Usuario;
import smedim.repository.MedicoRepository;
import smedim.repository.UsuarioRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Simeia Lima
 */
@ApplicationScoped
public class MedicoService implements Serializable {

    @Inject
    private MedicoDAO dao;
    @Inject
    private MedicoRepository medicoRepository;
    @Inject
    private UsuarioRepository usuarioRepository;

    @Transactional
    public boolean salvar(Medico medico, Usuario usuario) {
        try {
            Medico m = medicoRepository.saveAndFlush(medico);
            usuario.setNome(m.getNome());
            usuario.setPerfil('M');
            usuario.setMedico(m);
            usuarioRepository.saveAndFlush(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean salvar(Medico medico) {
        try {
            medicoRepository.save(medico);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean deletar(Medico medico) {
        try {
            medicoRepository.remove(medicoRepository.merge(medico));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public Medico obterPorId(Integer id) {
       return medicoRepository.findBy(id);
    }
    
    public List<Medico> obterTodos() {
        return medicoRepository.findAll();
    }
}
