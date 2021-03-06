package smedim.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * Created by Simeia Lima on 10/02/2016.
 */
@WebListener
public class BootStrap implements ServletContextListener {

    private static Logger log = Logger.getLogger(BootStrap.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Init bootstrap");
        JpaProducer.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
