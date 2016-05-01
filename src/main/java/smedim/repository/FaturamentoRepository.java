package smedim.repository;

import org.apache.deltaspike.data.api.*;
import smedim.entidade.Faturamento;
import smedim.entidade.Medico;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;
import java.util.List;

/**
 * Created by Simeia Lima on 05/03/2016.
 */
@Repository(forEntity = Faturamento.class)
@ApplicationScoped
public interface FaturamentoRepository extends FullEntityRepository<Faturamento, Integer> {
    @Query(named = Faturamento.BY_MEDICO)
    List<Faturamento> findByMedico(Medico medico, @FirstResult int first, @MaxResults int max);
    @Query(named = Faturamento.COUNT_BY_MEDICO)
    Long countByMedico(Medico medico);
    List<Faturamento> findByMedicoAndDataDoFaturamento(Medico medico, Date date);
}
