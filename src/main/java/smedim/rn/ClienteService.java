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
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import smedim.dao.ClienteDAO;
import smedim.entidade.Cliente;
import smedim.entidade.Cliente_;
import smedim.entidade.Prontuario;
import smedim.entidade.Usuario;
import smedim.repository.ClienteRepository;
import smedim.util.BeanUtil;
/**
 *
 * @author Mikael Lima
 */
@ApplicationScoped
public class ClienteService implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(ClienteService.class.getName());
    @Inject
    private ClienteDAO dao;
    @Inject
    private ProntuarioService rnP;
    @Inject
    private ClienteRepository clienteRepository;

    @Transactional
    public boolean salvar(Cliente cliente) {
        if (cliente.getId() == null || cliente.getId() == 0) {
            clienteRepository.save(cliente);
            Prontuario p = new Prontuario();
            p.setAntecedentesFamiliares("sem registros");
            p.setAntecedentesPessoais("sem registros");
            p.setCliente(cliente);
            return rnP.salvar(p);
        } else {
            return dao.alterar(cliente);
        }
    }

    @Transactional
    public boolean deletar(Cliente cliente) {
        try {
            cliente = clienteRepository.merge(cliente);
            clienteRepository.remove(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Cliente> autoComplete(String busca) {
        if (busca.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            return clienteRepository.findByNomeILike("%" + busca.toLowerCase() + "%", 20);
        }
    }

    public Cliente findById(Integer id) {
        return clienteRepository.findBy(id);
    }

    public List<Cliente> obterTodos() {
        Object o = BeanUtil.lerDaSessao("usuarioLogado");
        Usuario u = null;
        if (o != null && o instanceof Usuario)
            u = (Usuario) BeanUtil.lerDaSessao("usuarioLogado");
        ArrayList<Cliente> clientes = new ArrayList<>();
        if (u.getPerfil() == 'S' || u.getPerfil() == 'A') {
            clientes.addAll(dao.obterTodos());
        } else {
            for (Cliente cliente : dao.obterTodos()) {
                if ( u != null && cliente.getMedico() != null && u.getMedico().equals( cliente.getMedico() ) )
                    clientes.add(cliente);
            }
        }
        return clientes;
    }

    public Long count() {
        Usuario usuario = BeanUtil.getUsuarioLogado();
        Criteria<Cliente, Long> criteria = clienteRepository.criteria()
                .select(Long.class, clienteRepository.count(Cliente_.id));
        if (usuario != null) {
            if (usuario.getMedico() == null) {
                BeanUtil.mensagemError("Código 01", "O usuário não possui um perfil de médico.");
                return null;
            }
            if (usuario.getPerfil() == 'M') {
                criteria.eq(Cliente_.medico, usuario.getMedico());
            }
        } else {
            BeanUtil.mensagemFatal("Código 00", "Não foi possível obter o usuário logado.");
        }
        return criteria.getSingleResult();
    }

    public List<Cliente> list() {
        return list(-1, 0);
    }

    public List<Cliente> list(int start, int max) {
        TypedQuery<Cliente> query = createList();
        if (query == null)
            return Collections.EMPTY_LIST;
        else {
            if (start >= 0)
                query.setFirstResult(start);
            if (max > 0)
                query.setMaxResults(max);
            return query.getResultList();
        }
    }

    private TypedQuery<Cliente> createList() {
        Usuario usuario = BeanUtil.getUsuarioLogado();
        Criteria<Cliente, Cliente> criteria = clienteRepository.criteria();
        if (usuario != null) {
            if (usuario.getMedico() == null) {
                BeanUtil.mensagemError("Código 01", "O usuário não possui um perfil de médico.");
                return null;
            }
            if (usuario.getPerfil() == 'M') {
                criteria.eq(Cliente_.medico, usuario.getMedico());
            }
        } else {
            BeanUtil.mensagemFatal("Código 00", "Não foi possível obter o usuário logado.");
        }
        return criteria.createQuery();
    }
}
