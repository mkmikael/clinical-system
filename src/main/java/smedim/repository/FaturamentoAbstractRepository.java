package smedim.repository;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import smedim.entidade.Faturamento;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Simeia Lima on 30/04/2016.
 */
@Repository(forEntity = Faturamento.class)
@ApplicationScoped
public abstract class FaturamentoAbstractRepository extends AbstractFullEntityRepository<Faturamento, Integer> {
}
