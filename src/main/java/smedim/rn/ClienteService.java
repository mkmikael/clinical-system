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
import java.util.Map;
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

    public Long count() {
        return count(null);
    }

    public Long count(Map<String, Object> filters) {
        Usuario usuario = BeanUtil.getUsuarioLogado();
        Criteria<Cliente, Long> criteria = createCriteria(filters)
                .select(Long.class, clienteRepository.count(Cliente_.id));
        return criteria.getSingleResult();
    }

    public List<Cliente> list() {
        return list(-1, 0);
    }

    public List<Cliente> list(int start, int max) {
        return list(null, start, max);
    }

    public List<Cliente> list(Map<String, Object> filters, int start, int max) {
        Criteria<Cliente, Cliente> criteria = createCriteria(filters);
        TypedQuery<Cliente> query = criteria.createQuery();
        if (start >= 0)
            query.setFirstResult(start);
        if (max > 0)
            query.setMaxResults(max);
        return query.getResultList();
    }

    public Criteria<Cliente, Cliente> createCriteria(Map<String, Object> filters) {
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

        if (filters != null) {
            if (filters.containsKey("nome")) {
                criteria.likeIgnoreCase(Cliente_.nome, "%" + filters.get("nome").toString() + "%");
            }
        }
        return criteria;
    }

    public ClienteRepository getClienteRepository() {
        return clienteRepository;
    }
}
