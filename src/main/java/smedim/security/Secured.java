package smedim.security;

import javax.enterprise.inject.Stereotype;

/**
 * Created by Simeia Lima on 28/02/2016.
 */
@Stereotype
@org.apache.deltaspike.security.api.authorization.Secured(CustomAccessDecisionVoter.class)
public @interface Secured {
    String[] value();
}
