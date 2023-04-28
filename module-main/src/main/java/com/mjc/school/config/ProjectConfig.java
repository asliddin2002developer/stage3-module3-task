package com.mjc.school.config;


import com.mjc.school.repository.config.RepositoryConfig;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Import({RepositoryConfig.class})
@ComponentScan(basePackages={"com.mjc.school"})
public class ProjectConfig {
}



