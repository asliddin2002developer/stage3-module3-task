package com.mjc.school.repository.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    String confFile = "hibernate.cfg.xml";

    @Bean
    public SessionFactory sessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(confFile)
                .build();

        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return null;
    }
}
