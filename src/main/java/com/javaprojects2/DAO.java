package com.javaprojects2;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

@Component
public class DAO {

	//private static SessionFactory sessionFactory;

	public SessionFactory createSessionFactory() {
	    Configuration configuration = new Configuration();
	    configuration.configure().addAnnotatedClass(Person.class);
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()). build();
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}
}
