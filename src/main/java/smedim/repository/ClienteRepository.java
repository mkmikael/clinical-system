package smedim.repository;

import org.apache.deltaspike.data.api.FullEntityRepository;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import smedim.entidade.Cliente;
import smedim.entidade.Medico;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by Simeia Lima on 21/02/2016.
 */
@Repository(forEntity = Cliente.class)
@ApplicationScoped
public interface ClienteRepository extends FullEntityRepository<Cliente, Integer> {
    List<Cliente> findByNomeLikeAndMedicoOrderByNome(String nome, Medico medico, @MaxResults int maxResults);
    @Query(named = Cliente.BY_NOME_ILIKE)
    List<Cliente> findByNomeILike(String nome, @MaxResults int max);
}
