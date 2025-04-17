//!!!Не забывай коммитить/раскоммичивать открывающие и закрывающие части кода!!!

package org.example;

//import org.hibernate.envers.AuditReaderFactory;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

       /* //БЛОК ДЛЯ работы Валидации (проверяет какое-то условие для вводимых параметров.
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





        //БЛОК ДЛЯ заполнения БД

/*        //на !!!HQL!!!
        try (Session session = HibernateUtil.getSession()) {
      //try (Session session = HibernateUtilWithLongCodeForListener.getSession()) {
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
            employee1.setAge(0);
            employee1.setUniversities(universities1);
            employee1.setUniversities2(universities21);
            session.persist(employee1);

            Employee employee2 = new Employee();
            employee2.setFirstName("Kolya");
            employee2.setLastName("Bitten");
            employee2.setAge(22);
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





        //БЛОК ДЛЯ создания запроса к БД и выводу его результатов

        //!!!JPA!!!
        //берет настройки из "persistence.xml"
        // !!! Одновременно JPQL c HQL в одном файле использовать нельзя (хотя Criteria почему-то работает) т.к. класс Query, используемый для запросов:
        //- для HQL использует “import org.hibernate.query.Query”;
        //- для JPQL использует “import javax.persistence.Query”,
        //а "org.hibernate" перебивает "javax.persistence" !!!

      /*  EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");//где "my-pu" это самостоятельно придуманное в файле "persistence.xml" в блоке <persistence-unit> имя блока-сохраняемости
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();*/

            //!!!Criteria JPA!!!

          /*  //Запрос на выборку нескольких строк 2-х разных столбцов из корневой и 3-й внутренней таблицы
            // и потом из них переменных
            CriteriaBuilder cb3 = em.getCriteriaBuilder();
            CriteriaQuery<Tuple> cr3 = cb3.createTupleQuery(); //"Tuple" в 2-х местах - в типе и названии метода
            Root<Employee> root3 = cr3.from(Employee.class);

            //!начинаем обращаться к специальным классам созданым Matamodel-ю (Employee_,University_,City_), дающим возможность еще на этапе компиляции увидеть, что поля сущности(ломающие запрос) были изменены
            SetJoin<Employee, University> universityRoot3 = root3.join(Employee_.universities);
            Join<University, City> cityRoot3 = universityRoot3.join(University_.cities);
            cr3.multiselect(root3.get(Employee_.age).alias("ag"), cityRoot3.get(City_.town).alias("tow"))
                    .where(cb3.equal(root3.get(Employee_.firstName), "Kolya"));

            List<Tuple> result = em.createQuery(cr3).getResultList();
            String a = (String) result.get(1).get("tow");
            Integer b = (Integer) result.get(1).get("ag");
            System.out.println(a);
            System.out.println(b);*/

           /* //Запрос на выборку строк корневой таблицы и потом из них одной переменной
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
            Root<Employee> root = cr.from(Employee.class);
            cr.select(root);
            em.createQuery(cr).getResultList().forEach(System.out::println);
            List<Employee> l = em.createQuery(cr).getResultList();
            Employee l1 = l.get(1);
            String l2 = l1.getFirstName();
            System.out.println(l2);*/

           /*  //Запрос на выборку нескольких строк одного столбца из 3-й внутренней таблице и потом из них одной переменной
            CriteriaBuilder cb2 = em.getCriteriaBuilder();
            CriteriaQuery<String> cr2 = cb2.createQuery(String.class);
            Root<Employee> root2 = cr2.from(Employee.class);
            SetJoin<Employee, University> universityRoot = root2.join(Employee_.universities);
            Join<University, City> cityRoot = universityRoot.join(University_.cities);
            cr2.select(cityRoot.get(City_.town)).where(cb2.equal(root2.get(Employee_.firstName), "Kolya"));
            em.createQuery(cr2).getResultList().forEach(System.out::println);
            List<String> t = em.createQuery(cr2).getResultList();
            String t1 = t.get(0);
            System.out.println(t1);*/


           /* // Делаем запрос, используя механизм Графов позволяющий моментально в запросе менять тип загружаемых полей с "LAZY" на "EAGER"
            //В JPA по умолчанию "LAZY" используется для полей с аннотациями ManyToMany / OneToMany
            EntityGraph<Employee> entityGraph = em.createEntityGraph(Employee.class);
            entityGraph.addAttributeNodes("firstName");
            entityGraph.addAttributeNodes("lastName");
            entityGraph.addAttributeNodes("age");
            entityGraph.addAttributeNodes("universities");
            CriteriaBuilder cb1 = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cr1 = cb1.createQuery(Employee.class);
            Root<Employee> root1 = cr1.from(Employee.class);
            cr1.where(cb1.equal(root1.<Long>get("id"), 20));
            TypedQuery<Employee> typedQuery = em.createQuery(cr1);
            typedQuery.setHint("javax.persistence.loadgraph", entityGraph);
            Employee empl = typedQuery.getSingleResult();
            System.out.println(empl);*/


            //!!!JPQL!!!
            //Чтобы заработал закомментировать "import org.hibernate.query.Query;"

            /*//Запрос на выборку нескольких строк 2-х разных столбцов из корневой и 3-й внутренней таблицы
            // и потом из них переменных
            List<Tuple> query4 = em.createQuery("select c.town as tow, e.age as ag " +
                    "from Employee e " +
                    "join e.universities u " +
                    "inner join City c on u.cities = c.id " +
                    "where e.firstName = 'Kolya'", Tuple.class).getResultList();
            Integer a1 = (Integer) query4.get(0).get("ag");
            String b1 = (String) query4.get(0).get("tow");
            System.out.println(a1);
            System.out.println(b1);*/


           /* //Запрос на выборку строк корневой таблицы и потом из них одной переменной
            String qlQuery = "SELECT c FROM Employee c";
            Query query1 = em.createQuery(qlQuery);
            List<Employee> list1 = query1.getResultList();
            list1.stream().forEach((x) -> System.out.println(x));
            Employee ll1 = list1.get(1);
            String ll2 = ll1.getFirstName();
            System.out.println(ll2);*/

            /*//Запрос на выборку нескольких строк одного столбца из 3-й внутренней таблице и потом из них одной переменной
            Query query2 = em.createQuery("select e.firstName " +
                    "from Employee e " +
                    "join e.universities u " +
                    "inner join City c on u.cities = c.id " +
                    "where c.town = 'Lviv'");
             List<String> list2 = query2.getResultList();
             list2.stream().forEach((x) -> System.out.println(x));
             String tt1 = list2.get(0);
             System.out.println(tt1);*/

          /*   // Делаем запрос, используя механизм Графов позволяющий моментально в запросе менять тип загружаемых полей с "LAZY" на "EAGER"
            //В JPA по умолчанию "LAZY" используется для полей с аннотациями ManyToMany / OneToMany
            EntityGraph<Employee> entityGraph = em.createEntityGraph(Employee.class);
            entityGraph.addAttributeNodes("firstName");
            entityGraph.addAttributeNodes("lastName");
            entityGraph.addAttributeNodes("age");
            entityGraph.addSubgraph("universities").addAttributeNodes("name");
            Employee list3 = em.createQuery("select e from Employee e where e.id = :id", Employee.class)
                    .setParameter("id", 20)
                    .setHint("javax.persistence.fetchgraph", entityGraph)
                    .getSingleResult();
            System.out.println(list3);*/

   /*         em.getTransaction().commit();

        } finally {

            em.close();
            emf.close();
        }*/

       //!!!HQL!!!
       //берет настройки из "hibernate.cfg.xml"

        List<Employee> list1 = null;
        List<String> list2 = null;
//        List<EmployeeUUID> list = null;
        try (Session session = HibernateUtil.getSession()) {
//        try (Session session = HibernateUtilWithLongCodeForListener.getSession()) {
            session.beginTransaction();

            //Запрос на выборку нескольких строк 2-х разных столбцов из корневой и 3-й внутренней таблицы
            // и потом из них переменных
            List<Tuple> query4 = session.createQuery("select c.town as tow, e.age as ag " +
                    "from Employee e " +
                    "join e.universities u " +
                    "inner join City c on u.cities = c.id " +
                    "where e.firstName = 'Kolya'", Tuple.class).getResultList();
            Integer a1 = (Integer) query4.get(0).get("ag");
            String b1 = (String) query4.get(0).get("tow");
            System.out.println(a1);
            System.out.println(b1);


          /*  //Запрос на выборку строк корневой таблицы и потом из них одной переменной + доп.настройки
            Query query1 = session.createQuery("from Employee");
//            Query query = session.createQuery("FROM EmployeeUUID");
            //!!! доп.настройки запросов HQL (в том числе и идущие дальше) обычно делают приложение непереносимым между
            // базами данных, если только код, добавляющий их, сначала не проверяет диалект!!!
            query1.setHint( "org.hibernate.readOnly", true ); //определяет, что сущности и коллекции, загруженные этим запросом, должны быть помечены как доступные только для чтения
            query1.setHint("javax.persistence.query.timeout", 2000 ); //время ожидания зароса в миллисекундах
            query1.setCacheable(true); //включаем для запроса Кеш запросов (настроенный в "pom.xml" и "*.cfg.xml")
            query1.setLockMode(LockModeType.PESSIMISTIC_WRITE); // устанавливаем для запроса пессимистическую блокировку "WRITE"
            list1 = (List<Employee>) query1.list();
            list1.stream().forEach((x) -> System.out.println(x));
            Employee lll1 = list1.get(1);
            String lll2 = lll1.getFirstName();
            System.out.println(lll2);*/

           /* //Запрос на выборку нескольких строк одного столбца из 3-й внутренней таблице и потом из них одной переменной
            Query query2 = session.createQuery("select e.firstName " +
                    "from Employee e " +
                    "join e.universities u " +
                    "inner join City c on u.cities = c.id " +
                    "where c.town = 'Lviv'");
            list2 = (List<String>) query2.list();
            list2.stream().forEach((x) -> System.out.println(x));
            String ww1 = list2.get(0);
            System.out.println(ww1);*/


//            list = (List<EmployeeUUID>) query.list();


            session.getTransaction().commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }





        //        //БЛОК ДЛЯ удаления записей-объектов из БД

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





        //БЛОК ДЛЯ Envers, покажет когда и какие изменения были сделаны в таблице Employee.
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
