package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "employee")
//@Audited /* подключаем Envers */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int    id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;
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
