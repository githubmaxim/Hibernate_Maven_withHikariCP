package org.example;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;


public class HibernateUtilWithLongCodeForListener {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static Session getSession() {
        if (sessionFactory == null) {
            try {
                BootstrapServiceRegistry bootstrapRegistry =
                        new BootstrapServiceRegistryBuilder()
                                .applyIntegrator(new EventListenerIntegrator())
                                .build();

                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder(bootstrapRegistry);

               /* Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/BORAJI?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "admin");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                registryBuilder.applySettings(settings);*/
                registryBuilder.configure();

                registry = registryBuilder.build();

                MetadataSources sources = new MetadataSources(registry)
                        .addAnnotatedClass(Employee.class);

                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                e.printStackTrace();
            }
        }
        return sessionFactory.openSession();
    }

    public static void shutDown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
