package org.example;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            /*Блок для включения слушателя событий.
            --Почему-то не загружает в класс Configuration метод setListener(), который должен
            --подвязать под операции save-update событие наш класс-обработчик событий SaveCounter!!!!!
            --Есть в папке Литература еще один пример создания и подвязывания обработчика событий, но
            --там все очень многострочно.
            Configuration config = new Configuration();
            config.setListener("save-update", new SaveCounter());
            SessionFactory factory = config.configure().buildSessionFactory();*/

// Это самое быстрое создание sessionFactory с автоматическим подключением конфигурации (.configure()) из hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public static void shutDown() {
        sessionFactory.close();
    }
}