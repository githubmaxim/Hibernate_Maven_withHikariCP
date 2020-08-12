package org.example;

import org.hibernate.Session;
//import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.query.Query;

import javax.persistence.LockModeType;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
       /* //блок работы Валидации
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        ForValidator  forValidator = new ForValidator();
        forValidator.setA(0);
        forValidator.setB(5);
        System.out.println(forValidator.summ());
        Set<ConstraintViolation<ForValidator>> violations = validator.validate(forValidator);
        for (ConstraintViolation<ForValidator> violation : violations) {
            System.out.println(violation.getMessage());
        }*/

        //блок для заполнения БД
        try (Session session = HibernateUtil.getSession()) {
//        try (Session session = HibernateUtilWithLongCodeForListener.getSession()) {
            session.beginTransaction();

            University university1 = new University();
            university1.setName("KPI");
            session.persist(university1);
            University university2 = new University();
            university2.setName("MAUP");
            session.persist(university2);
            University university3 = new University();
            university3.setName("UNIVER");
            session.persist(university3);

            Set<University> universities1 = new HashSet<>();
            universities1.add(university1);
            universities1.add(university2);
            Set<University> universities2 = new HashSet<>();
            universities2.add(university2);
            universities2.add(university3);


            University2 university21 = new University2();
            university21.setName2("ForDelete");
            session.persist(university21);
            List<University2> universities21 = new ArrayList<University2>();
            universities21.add(university21);


            Employee employee1 = new Employee();
            employee1.setFirstName("Vova");
            employee1.setLastName("Nik");
            employee1.setAge("19");
            employee1.setUniversities(universities1);
            employee1.setUniversities2(universities21);
            session.save(employee1);


            Employee employee2 = new Employee();
            employee2.setFirstName("Kolya");
            employee2.setLastName("Bitten");
            employee2.setAge("22");
            employee2.setUniversities(universities2);
            employee2.setUniversities2(universities21);
            session.save(employee2);

            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }


        //Блок для создания запроса к БД и выводу его результатов
        List<Employee> list = null;
//        List<EmployeeUUID> list = null;
        try (Session session = HibernateUtil.getSession()) {
//        try (Session session = HibernateUtilWithLongCodeForListener.getSession()) {
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
            for (Employee employee : list) {
//            for (EmployeeUUID employee: list) {
                System.out.println(employee);
            }
        }

/*        //блок для удаления записей-объектов из БД
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            //Удалит объект в корневой сущности и записи в промежуточных таблицах, но оставит записи в связанных таблицах
            //session.createQuery("DELETE Employee WHERE id = :id").setParameter("id", 72).executeUpdate();

           *//* Employee employee = entityManager.find(Employee.class, 73);
            entityManager.remove(employee);
            flushAndClear();*//*

           //Удалит объект в корневой сущности и все записи по этому объекту в промежуточных и связанной таблице/ах, при установленном
            //в корневой сущности "cascade=CascadeType.ALL".Если это не установить, то в связанной таблице/ах ничего не удалит
            Employee employee = session.load(Employee.class, 74);
            if (employee != null) { session.delete(employee);}
            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }*/

        //блок работы Envers, покажет когда и какие изменения были сделаны в таблице Employee
        //Не увидит если провести изменения на стороне БД!
//        try (Session session = HibernateUtil.getSession()) {
////        try (Session session = HibernateUtilWithLongCodeForListener.getSession()) {
//            session.beginTransaction();
//            System.out.println("\n------This is block Envers------");
//            AuditReaderFactory
//                    .get(session)
//                    .createQuery()
//                    .forRevisionsOfEntity(Employee.class, false, true)
//                    .getResultList()
//                    .forEach(r -> {
//                        Object[] v = (Object[]) r;
//                        System.out.println(v[0]);
//                        System.out.println(v[1]);
//                        System.out.println(v[2]);
//                    });
//            session.getTransaction().commit();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }

        HibernateUtil.shutDown();
//        HibernateUtilWithLongCodeForListener.shutDown();
    }
}
