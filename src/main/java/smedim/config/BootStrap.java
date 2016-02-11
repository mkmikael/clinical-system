package smedim.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Simeia Lima on 10/02/2016.
 */
@ApplicationScoped
public class BootStrap {

    public void onStartup(@Observes @Initialized(value = ApplicationScoped.class) EntityManagerFactory entityManagerFactory) {

    }
}
