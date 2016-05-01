package smedim.repository;

import org.apache.deltaspike.data.api.FullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import smedim.entidade.Usuario;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Simeia Lima on 05/03/2016.
 */
@Repository
@ApplicationScoped
public interface UsuarioRepository extends FullEntityRepository<Usuario, Integer> {
}
