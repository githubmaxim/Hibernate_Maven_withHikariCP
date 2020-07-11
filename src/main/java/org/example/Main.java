package org.example;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.LockModeType;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            Employee employee = new Employee();
//            EmployeeUUID employee = new EmployeeUUID();
            employee.setFirstName("Vova");
            employee.setLastName("Nik");

            session.save(employee);

            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        List<Employee> list = null;
//        List<EmployeeUUID> list = null;

        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            Query query = session.createQuery("FROM Employee");
//            Query query = session.createQuery("FROM EmployeeUUID");
            query.setCacheable(true); //тут мы, для нашего запроса, включаем Кеш запросов (настроенный в "pom.xml" и "*.cfg.xml")
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE); // т м, д н з, устанавливаем пессимистическую блокировку "WRITE"
            list = (List<Employee>) query.list();
//            list = (List<EmployeeUUID>) query.list();

            session.getTransaction().commit();
        } catch (Throwable e) {
        e.printStackTrace();
        }

        if (list != null && !list.isEmpty()) {
            for (Employee employee: list) {
//            for (EmployeeUUID employee: list) {
                System.out.println(employee);
            }
        }

        HibernateUtil.shutDown();


    }
}
