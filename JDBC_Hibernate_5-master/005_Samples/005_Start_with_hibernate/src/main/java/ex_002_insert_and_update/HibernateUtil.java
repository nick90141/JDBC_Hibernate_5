package ex_002_insert_and_update;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

//import javax.security.auth.login.Configuration;
//import java.util.logging.Logger;


/**
 * Created by Asus on 01.11.2017.
 */

public class HibernateUtil {
    private static SessionFactory factory;
    private static final Logger LOG = (Logger) LoggerFactory.getLogger(HibernateUtil.class);

    static {
        try {
            factory = new Configuration()
                    .configure("ex_02_hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (HibernateException e) {
            LOG.error("Error initializing Hibernate", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

}
