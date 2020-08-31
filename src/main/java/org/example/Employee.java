package org.example;

import lombok.*;
//import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "employee")
//@Audited /* подключаем Envers */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"firstName", "lastName", "age", "universities"}) //в файле University.java вывод поля ссылающегося на Employee.java не прописываю, иначе зациклится
@ToString (of = {"firstName", "lastName", "age", "universities"}) //"(of =" идальше нужно писать, т.к. тут есть поля
// связей с другими таблицами (universities и universities2) и их механизм этой аннотации вывести не может
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Version //устанавливается оптимистическая блокировка для полей класса
    private int version;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String age;


    @ElementCollection
    private Map<Integer, String> addresses = new HashMap<>(); //создает отдельную таблицу с ключами для связки с employee в БД


    @ManyToMany
    @JoinTable(name = "Employee_University", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "university_id"))
    private Set<University> universities;

    @ManyToMany (cascade=CascadeType.ALL)
    @JoinTable(name = "Employee_University2", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "university_id"))
    private List<University2> universities2;
}

/* ИЛИ ТОЖЕ САМОЕ, НО БЕЗ Lombok
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int    id;
    public int    getId() {return id;}
    public void   setId(int id) {this.id = id;}

    @Version //устанавливается оптимистическая блокировка для полей класса
    private int version;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    public Employee() {}

    public String getFirstName() {return firstName;}
    public void   setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void   setLastName(String lastName) {this.lastName = lastName;}

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}*/
