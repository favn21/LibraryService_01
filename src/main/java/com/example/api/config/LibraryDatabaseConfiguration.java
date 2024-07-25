package com.example.api.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Configuration;
import com.example.api.db.Book;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LibraryDatabaseConfiguration {

    private static final SessionFactory sessionFactory = getSessionFactory();
    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();
    private static Session session;


    private LibraryDatabaseConfiguration() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();

                configuration.addAnnotatedClass(Book.class);

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                Metadata metadata = new MetadataSources(serviceRegistry)
                        .getMetadataBuilder()
                        .build();

                return metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        if (session == null) {
            try {
                session = getSessionFactory().openSession();

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return session;
    }
    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("LibraryPersistenceUnit");
    }


}
