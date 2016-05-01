package smedim.security;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Created by Simeia Lima on 28/02/2016.
 */
@Stereotype
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@org.apache.deltaspike.security.api.authorization.Secured(CustomAccessDecisionVoter.class)
public @interface Secured {
    String[] value();
}
