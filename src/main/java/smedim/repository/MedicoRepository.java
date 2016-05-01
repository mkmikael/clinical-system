package smedim.repository;

import org.apache.deltaspike.data.api.FullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import smedim.entidade.Medico;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Simeia Lima on 05/03/2016.
 */
@Repository(forEntity = Medico.class)
@ApplicationScoped
public interface MedicoRepository extends FullEntityRepository<Medico, Integer> {}
