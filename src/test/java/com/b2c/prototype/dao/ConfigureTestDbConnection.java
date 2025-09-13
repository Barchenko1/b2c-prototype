package com.b2c.prototype.dao;

import com.tm.core.configuration.dbType.DatabaseConfigurationAnnotationClass;
import com.tm.core.configuration.dbType.DatabaseType;
import com.tm.core.configuration.dbType.DatabaseTypeConfiguration;
import com.tm.core.configuration.entityManager.EntityManagerFactoryManager;
import com.tm.core.configuration.entityManager.IEntityManagerFactoryManager;
import com.tm.core.configuration.session.ISessionFactoryManager;
import com.tm.core.configuration.session.SessionFactoryManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;

public class ConfigureTestDbConnection {

    private static final String CONFIGURATION_FILE_NAME = "hikari.hibernate.cfg.xml";

    public ConfigureTestDbConnection() {
    }

    public static SessionFactory getSessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactory(DatabaseType.WRITE, CONFIGURATION_FILE_NAME);
    }

    public static EntityManager getEntityManager() {
        IEntityManagerFactoryManager entityManagerFactoryManager = EntityManagerFactoryManager.getInstance(getDatabaseTypeConfiguration());
        EntityManagerFactory entityManagerFactory = entityManagerFactoryManager.getEntityManagerFactory(DatabaseType.WRITE, CONFIGURATION_FILE_NAME);
        return entityManagerFactory.createEntityManager();
    }

    private static DatabaseTypeConfiguration getDatabaseTypeConfiguration() {
        DatabaseConfigurationAnnotationClass[] databaseConfigurationAnnotationClass =
                new DatabaseConfigurationAnnotationClass[] {
                        new DatabaseConfigurationAnnotationClass("hikari.hibernate.cfg.xml"),
                };

        return new DatabaseTypeConfiguration(DatabaseType.WRITE, databaseConfigurationAnnotationClass);
    }
}
