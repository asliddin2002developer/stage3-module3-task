package com.mjc.school.repository.model.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


public class TagModelTest {

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
        // Create a new TagModel entity
        TagModel tagModel = new TagModel();
        tagModel.setName("Test Tag");

        // Save the entity to the database
        session.beginTransaction();
        session.save(tagModel);
        session.getTransaction().commit();

        // Retrieve the entity from the database using the generated ID
        TagModel retrievedTagModel = session.get(TagModel.class, tagModel.getId());
        assertNotNull(retrievedTagModel);

        // Update the entity
        retrievedTagModel.setName("Updated Tag");

        // Save the updated entity to the database
        session.beginTransaction();
        session.update(retrievedTagModel);
        session.getTransaction().commit();

        // Delete the entity from the database
        session.beginTransaction();
        session.delete(retrievedTagModel);
        session.getTransaction().commit();

        // Verify that the entity was deleted
        TagModel deletedTagModel = session.get(TagModel.class, tagModel.getId());
        assertNull(deletedTagModel);
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

