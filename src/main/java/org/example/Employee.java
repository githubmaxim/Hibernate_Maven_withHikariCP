package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
//@Audited /* подключаем Envers */
@Getter
@Setter
//@ToString - эта аннотация не работает и при попытке вывода на экран выдает ошибку
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Version //устанавливается оптимистическая блокировка для полей класса
    private int version;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "AGE")
    private String age;

    @ManyToMany
    @JoinTable(name = "Employee_University", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "university_id"))
    private List<University> universities;

    @ManyToMany (cascade=CascadeType.ALL)
    @JoinTable(name = "Employee_University2", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "university_id"))
    private List<University2> universities2;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
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
