package smedim.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Simeia Lima on 10/02/2016.
 */

public class JpaProducer {

    private static final String PU = "SMEDIMPU";
    private static EntityManagerFactory entityManagerFactory;

    public static void init() {
        if (entityManagerFactory == null)
            entityManagerFactory = Persistence.createEntityManagerFactory(PU);
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