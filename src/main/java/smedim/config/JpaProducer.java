package smedim.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Simeia Lima on 10/02/2016.
 */

public class JpaProducer {

    private static final String PU = "SMEDIMPU";
    private static EntityManagerFactory entityManagerFactory;
    private static Logger log = Logger.getLogger(JpaProducer.class.getName());

    public static void init() {
        if (entityManagerFactory == null) {
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory(PU);
            } catch (Exception ex) {
                log.log(Level.SEVERE, "Verifique se o banco de dados está em execução.", ex);
            }
        }
    }

    @Produces
    @Default
    @ApplicationScoped
    public EntityManagerFactory createEntityManagerFactory() {
        init();
        return entityManagerFactory;
    }

    @Produces
    @Default
    @RequestScoped
    public EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    public void disposeEntityManager(@Disposes @Default EntityManager entityManager) {
        if (entityManager.isOpen())
            entityManager.close();
    }

    public void disposeEntityManagerFactory(@Disposes @Default EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory.isOpen())
            entityManagerFactory.close();
    }
}