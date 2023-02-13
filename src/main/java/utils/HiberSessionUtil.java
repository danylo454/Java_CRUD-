package utils;

import models.*;
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
//                configuration.addAnnotatedClass(Question.class);
//                configuration.addAnnotatedClass(QuestionItem.class);
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(Categories.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(ProductImages.class);
                configuration.addAnnotatedClass(Products.class);
                configuration.addAnnotatedClass(UserRole.class);
                configuration.addAnnotatedClass(UserRolePK.class);
                configuration.addAnnotatedClass(BasketsPK.class);
                configuration.addAnnotatedClass(Baskets.class);
                configuration.addAnnotatedClass(OrderItems.class);
                configuration.addAnnotatedClass(Orders.class);
                configuration.addAnnotatedClass(OrderStatuses.class);

                configuration.addAnnotatedClass(FilterNames.class);
                configuration.addAnnotatedClass(FilterValues.class);
                configuration.addAnnotatedClass(FilterNameGroups.class);
                configuration.addAnnotatedClass(FilterNameGroupsPK.class);


                configuration.addAnnotatedClass(FiltersPK.class);
                configuration.addAnnotatedClass(Filters.class);





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
