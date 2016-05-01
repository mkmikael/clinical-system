package smedim.security;

import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import smedim.entidade.Usuario;
import smedim.util.BeanUtil;

/**
 * Created by Simeia Lima on 28/02/2016.
 */
@ApplicationScoped
public class CustomAccessDecisionVoter implements AccessDecisionVoter {
    @Inject
    private Logger log;

    @Override
    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext voterContext) {
        Secured annotation = voterContext.getMetaDataFor(Secured.class.getName(), Secured.class);
        String[] roles = annotation.value();
        Set<SecurityViolation> violations = new HashSet<>();
        Usuario usuario = BeanUtil.getUsuarioLogado();
        if (usuario != null) {
            if (!Arrays.asList(roles).contains(usuario.getPerfil().toString())) {
                addViolation(violations, "Permissão negada! O usuário não possui credenciais para acessar á página.");
            }
        } else {
            addViolation(violations, "Não existe usuário logado.");
        }
        return violations;
    }

    private void addViolation(Set<SecurityViolation> violations, final String message) {
        violations.add(new SecurityViolation() {
            @Override
            public String getReason() {
                return message;
            }
        });
    }
}
