package com.b2c.prototype.dao;

import com.tm.core.configuration.manager.DatabaseConfigurationAnnotationClass;
import com.tm.core.configuration.manager.DatabaseType;
import com.tm.core.configuration.manager.DatabaseTypeConfiguration;
import com.tm.core.configuration.manager.ISessionFactoryManager;
import com.tm.core.configuration.manager.SessionFactoryManager;
import org.hibernate.SessionFactory;

public class ConfigureSessionFactoryTest {

    private static final String CONFIGURATION_FILE_NAME = "hikari.hibernate.cfg.xml";
    private static final String CONFIGURATION_EMBEDDED_DB_FILE_NAME = "embedded.hibernate.cfg.xml";

    public ConfigureSessionFactoryTest() {
    }

    public static SessionFactory getSessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactorySupplier(DatabaseType.WRITE, CONFIGURATION_FILE_NAME).get();
    }

    public static SessionFactory getEmbeddedSessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactorySupplier(DatabaseType.WRITE, CONFIGURATION_EMBEDDED_DB_FILE_NAME).get();
    }

    private static DatabaseTypeConfiguration getDatabaseTypeConfiguration() {
        DatabaseConfigurationAnnotationClass[] databaseConfigurationAnnotationClass =
                new DatabaseConfigurationAnnotationClass[] {
                        new DatabaseConfigurationAnnotationClass("hikari.hibernate.cfg.xml"),
                        new DatabaseConfigurationAnnotationClass("embedded.hibernate.cfg.xml")
                };

        return new DatabaseTypeConfiguration(DatabaseType.WRITE, databaseConfigurationAnnotationClass);
    }
}
