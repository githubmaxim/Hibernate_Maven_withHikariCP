package org.example;

import org.hibernate.Session;
//import org.hibernate.envers.AuditReaderFactory;
//import org.hibernate.query.Query;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

       /* //Блок работы Валидации (проверяет какое-то условие для вводимых параметров.
          // Действует как пробрасываемые исключения - реакция на событие из серии "потом проверят и напишут")
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



/*        //Блок для заполнения БД

        //на !!!HQL!!!
        try (Session session = HibernateUtil.getSession()) {
//        try (Session session = HibernateUtilWithLongCodeForListener.getSession()) {
            session.beginTransaction();

            City city1 = new City();
            city1.setTown("Kiev");
            City city2 = new City();
            city2.setTown("Lviv");
            session.persist(city1); //лучше использовать "persist", т.к. с "save"/"saveorupdate" бывают проблемы
            session.persist(city2);


            University university1 = new University();
            university1.setName("KPI");
            university1.setCities(city1);
            University university2 = new University();
            university2.setName("MAUP");
            university2.setCities(city2);
            University university3 = new University();
            university3.setName("UNIVER");
            university3.setCities(city1);
            session.persist(university1);
            session.persist(university2);
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
            employee1.setAge("0");
            employee1.setUniversities(universities1);
            employee1.setUniversities2(universities21);
            session.persist(employee1);

            Employee employee2 = new Employee();
            employee2.setFirstName("Kolya");
            employee2.setLastName("Bitten");
            employee2.setAge("22");
            employee2.setUniversities(universities2);
            employee2.setUniversities2(universities21);
            session.persist(employee2);

            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }*/

        //на !!!JPA!!!
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-xu");//где "my-pu" это самостоятельно придуманное в файле "persistence.xml" в блоке <persistence-unit> имя блока-сохраняемости
//        EntityManager entityManager = emf.createEntityManager();
//        try {
//            entityManager.getTransaction().begin();
//
//           //везде по тексту меняю "session" на "entityManager"
//
//            entityManager.getTransaction().commit();
//        } finally {
//            entityManager.close();
//            emf.close();
//        }





        //Блок для создания запроса к БД и выводу его результатов

        //!!!JPA!!!
        //берет настройки из "persistence.xml"
        // !!! Одновременно JPQL c HQL в одном файле использовать нельзя (хотя Criteria плчему-то работает) т.к. класс Query, используемый для запросов:
        //- для HQL использует “import org.hibernate.query.Query”;
        //- для JPQL использует “import javax.persistence.Query”,
        //а "org.hibernate" перебивает "javax.persistence" !!!

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");//где "my-pu" это самостоятельно придуманное в файле "persistence.xml" в блоке <persistence-unit> имя блока-сохраняемости
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            //!!!Criteria JPA!!!
            /*CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
            Root<Employee> root = cr.from(Employee.class);
            cr.select(root);
            entityManager.createQuery(cr).getResultList().forEach(System.out::println);*/

            // Делаем запрос, используя механизм Графов позволяющий моментально в запросе менять тип загружаемых полей с "LAZY" на "EAGER"
            //В JPA по умолчанию "LAZY" используется для полей с аннотациями ManyToMany / OneToMany
            EntityGraph<Employee> entityGraph = entityManager.createEntityGraph(Employee.class);
            entityGraph.addAttributeNodes("firstName");
            entityGraph.addAttributeNodes("lastName");
            entityGraph.addAttributeNodes("age");
            entityGraph.addAttributeNodes("universities");
            CriteriaBuilder cb1 = entityManager.getCriteriaBuilder();
            CriteriaQuery<Employee> cr1 = cb1.createQuery(Employee.class);
            Root<Employee> root1 = cr1.from(Employee.class);
            cr1.where(cb1.equal(root1.<Long>get("id"), 20));
            TypedQuery<Employee> typedQuery = entityManager.createQuery(cr1);
            typedQuery.setHint("javax.persistence.loadgraph", entityGraph);
            Employee empl = typedQuery.getSingleResult();
            System.out.println(empl);



 /*           //!!!JPQL!!!
            String qlQuery = "SELECT c FROM Employee c";
            Query query1 = entityManager.createQuery(qlQuery);
            List<Employee> list1 = query1.getResultList();
            list1.stream().forEach((x) -> System.out.println(x));

            Query query2 = entityManager.createQuery("select e.firstName " +
                    "from Employee e " +
                    "join e.universities u " +
                    "inner join City c on u.cities = c.id " +
                    "where c.town = 'Lviv'");
             List<String> list2 = query2.getResultList();
             list2.stream().forEach((x) -> System.out.println(x));

             // Делаем запрос, используя механизм Графов позволяющий моментально в запросе менять тип загружаемых полей с "LAZY" на "EAGER"
            //В JPA по умолчанию "LAZY" используется для полей с аннотациями ManyToMany / OneToMany
            EntityGraph<Employee> entityGraph = entityManager.createEntityGraph(Employee.class);
            entityGraph.addAttributeNodes("firstName");
            entityGraph.addAttributeNodes("lastName");
            entityGraph.addAttributeNodes("age");
            entityGraph.addSubgraph("universities").addAttributeNodes("name");
            Employee list3 = entityManager.createQuery("select e from Employee e where e.id = :id", Employee.class)
                    .setParameter("id", 20)
                    .setHint("javax.persistence.fetchgraph", entityGraph)
                    .getSingleResult();
            System.out.println(list3);*/

            entityManager.getTransaction().commit();

        } finally {

            entityManager.close();
            emf.close();
        }

       //!!!HQL!!!
       //берет настройки из "hibernate.cfg.xml"

 /*
        List<Employee> list1 = null;
        List<String> list2 = null;
//        List<EmployeeUUID> list = null;
        try (Session session = HibernateUtil.getSession()) {
//        try (Session session = HibernateUtilWithLongCodeForListener.getSession()) {
            session.beginTransaction();

            Query query1 = session.createQuery("from Employee");
//            Query query = session.createQuery("FROM EmployeeUUID");
            Query query2 = session.createQuery("select e.firstName " +
                    "from Employee e " +
                    "join e.universities u " +
                    "inner join City c on u.cities = c.id " +
                    "where c.town = 'Lviv'");

            //!!! доп.настройки запросов HQL (в том числе и идущие дальше) обычно делают приложение непереносимым между
            // базами данных, если только код, добавляющий их, сначала не проверяет диалект!!!
            query1.setHint( "org.hibernate.readOnly", true ); //определяет, что сущности и коллекции, загруженные этим запросом, должны быть помечены как доступные только для чтения
            query1.setHint("javax.persistence.query.timeout", 2000 ); //время ожидания зароса в миллисекундах
            query1.setCacheable(true); //включаем для запроса Кеш запросов (настроенный в "pom.xml" и "*.cfg.xml")
            query1.setLockMode(LockModeType.PESSIMISTIC_WRITE); // устанавливаем для запроса пессимистическую блокировку "WRITE"

            list1 = (List<Employee>) query1.list();
            list2 = (List<String>) query2.list();
//            list = (List<EmployeeUUID>) query.list();


            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (list1 != null && !list1.isEmpty()) {
            for (Employee employee : list1) {
//            for (EmployeeUUID employee: list) {
                System.out.println(employee);
            }
        }
        if (list2 != null && !list2.isEmpty()) {
            for (String fn : list2) {
//            for (EmployeeUUID employee: list) {
                System.out.println(fn);
            }
        }*/





 //        //блок для удаления записей-объектов из БД


        //!!!HQL!!!
//        try (Session session = HibernateUtil.getSession()) {
//            session.beginTransaction();
//            //Удалит объект в корневой сущности и записи в промежуточных таблицах, но оставит записи в связанных таблицах
//           session.createQuery("DELETE University WHERE id = :id").setParameter("id", 128).executeUpdate();
//
//            //Удалит объект в корневой сущности и все записи по этому объекту в промежуточных и связанной таблице/ах, при установленном
//            //в корневой сущности "cascade=CascadeType.ALL".Если это не установить, то в связанной таблице/ах ничего не удалит
//            University employee = session.load(University.class, 129);
//            if (employee != null) {session.delete(employee);}
//            session.getTransaction().commit();
//       } catch (Throwable e) {
//            e.printStackTrace();
//        }






        //Блок работы Envers, покажет когда и какие изменения были сделаны в таблице Employee.
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

/*        HibernateUtil.shutDown();*/
//        HibernateUtilWithLongCodeForListener.shutDown();
    }
}
