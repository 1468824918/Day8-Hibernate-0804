package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by dllo on 17/10/31.
 */
public class HibernateUtil {
    private static final SessionFactory SF;
    static {
        SF = new Configuration().configure().buildSessionFactory();
    }
    public static SessionFactory getSessionFactory (){
        return SF;
    }
}
