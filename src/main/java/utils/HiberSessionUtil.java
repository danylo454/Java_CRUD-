package utils;

import models.Role;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HiberSessionUtil {
    private static SessionFactory sessionFactory;
    public HiberSessionUtil(){}
    public  static  SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            try{
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Role.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory();

            }catch (Exception ex){
                System.out.printf("Error connection"+ ex.toString());
            }
        }
        return sessionFactory;
    }
}