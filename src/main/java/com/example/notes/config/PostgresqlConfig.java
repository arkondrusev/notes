package com.example.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@Configuration
public class PostgresqlConfig {

    @Bean
    public LocalSessionFactoryBean getLocalSessionFactoryBean() {
        Properties hp = new Properties();
        hp.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hp.setProperty("hibernate.show_sql", "true");
        hp.setProperty("hibernate.hbm2ddl", "update");

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://31.172.72.189/notesdb");
        ds.setUsername("postgres");
        ds.setPassword("Ingener312");

        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(ds);
        lsfb.setHibernateProperties(hp);
        lsfb.setPackagesToScan("com.example.notes.model");

        return lsfb;
    }

    @Bean
    public PlatformTransactionManager getPlatformTransactionManager() {
        HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(getLocalSessionFactoryBean().getObject());
        return htm;
    }

}
