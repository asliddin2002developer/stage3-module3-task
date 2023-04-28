package com.mjc.school.repository;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;


public class HibernateTest {

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
    void testNewsCreate(){
        session.beginTransaction();

        AuthorModel authorModel = new AuthorModel("TestUser", LocalDateTime.now(), LocalDateTime.now());
        Long authorId = (Long) session.save(authorModel);

        NewsModel newsModel = new NewsModel("Why people cry?", "Lorem ipsum is dummy text....", authorModel, LocalDateTime.now(), LocalDateTime.now());
        Long newsId = (Long) session.save(newsModel);

        session.getTransaction().commit();

        Assertions.assertTrue(authorId > 0);
        Assertions.assertTrue(newsId > 0);
    }

    @Test
    void testNewsList(){

    }

    @Test
    void testNewsUpdate(){

    }

    @Test
    void testNewsDelete(){

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
