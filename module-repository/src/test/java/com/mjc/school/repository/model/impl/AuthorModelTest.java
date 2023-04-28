package com.mjc.school.repository.model.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


public class AuthorModelTest {

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
        // Create a new AuthorModel entity
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName("Test User");

        // Save the entity to the database
        session.beginTransaction();
        session.save(authorModel);
        session.getTransaction().commit();

        // Retrieve the entity from the database using the generated ID
        AuthorModel retrievedAuthorModel = session.get(AuthorModel.class, authorModel.getId());
        assertNotNull(retrievedAuthorModel);

        // Update the entity
        retrievedAuthorModel.setName("Updated User");

        // Save the updated entity to the database
        session.beginTransaction();
        session.update(retrievedAuthorModel);
        session.getTransaction().commit();

        // Delete the entity from the database
        session.beginTransaction();
        session.delete(retrievedAuthorModel);
        session.getTransaction().commit();

        // Verify that the entity was deleted
        AuthorModel deletedAuthorModel = session.get(AuthorModel.class, authorModel.getId());
        assertNull(deletedAuthorModel);
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

