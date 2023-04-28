package com.mjc.school.repository.model.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


public class NewsModelTest {

    private static SessionFactory sessionFactory;
    private Session session;
    static String confFile = "hibernate.cfg.xml";

    @BeforeAll
    protected static void setUp(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(confFile) // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @AfterAll
    protected static void tearDown(){
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testCreateReadUpdateDelete() {
        // Create a new NewsModel entity
        NewsModel newsModel = new NewsModel();
        newsModel.setTitle("Test Title");
        newsModel.setContent("Test Content");

        // Save the entity to the database
        session.beginTransaction();
        session.save(newsModel);
        session.getTransaction().commit();

        // Retrieve the entity from the database using the generated ID
        NewsModel retrievedNewsModel = session.get(NewsModel.class, newsModel.getId());
        assertNotNull(retrievedNewsModel);

        // Update the entity
        retrievedNewsModel.setTitle("Updated Title");
        retrievedNewsModel.setContent("Updated Content");

        // Save the updated entity to the database
        session.beginTransaction();
        session.update(retrievedNewsModel);
        session.getTransaction().commit();

        // Delete the entity from the database
        session.beginTransaction();
        session.delete(retrievedNewsModel);
        session.getTransaction().commit();

        // Verify that the entity was deleted
        NewsModel deletedNewsModel = session.get(NewsModel.class, newsModel.getId());
        assertNull(deletedNewsModel);
    }

    @BeforeEach
    public void openSession(){
        session = sessionFactory.openSession();
    }

    @AfterEach
    public void closeSession(){
        if(session != null){
            session.close();
        }
    }


}
