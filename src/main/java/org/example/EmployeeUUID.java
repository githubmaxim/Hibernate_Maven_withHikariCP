//Created "id" with using UUID

package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "employeeUUID")
//@Audited /* подключаем Envers */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmployeeUUID {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;
}

/*  ИЛИ ТОЖЕ САМОЕ, НО БЕЗ Lombok
@Entity
@Table(name = "employeeUUID")
public class EmployeeUUID {

    @Id
    @GeneratedValue
    private UUID id;
    public UUID    getId() {return id;}
    public void   setId(UUID id) {this.id = id;}

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    public EmployeeUUID() {}

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
