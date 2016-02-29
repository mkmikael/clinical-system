package smedim.repository;

import org.apache.deltaspike.data.api.FullEntityRepository;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import smedim.entidade.Medico;
import smedim.entidade.Prontuario;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Created by Simeia Lima on 27/02/2016.
 */
@Repository
@ApplicationScoped
public interface ProntuarioRepository extends FullEntityRepository<Prontuario, Integer> {

    @Query(named = Prontuario.BY_CLIENTE_NOME_ILIKE)
    List<Prontuario> findByNomeILikeAndMedico(String nome, Medico medico, @MaxResults int max);
}
