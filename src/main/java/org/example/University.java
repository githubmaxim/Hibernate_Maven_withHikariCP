package org.example;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "university")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@ToString(of = {"name"}) //"(of =" идальше нужно писать, т.к. тут есть поле связи с другой таблицей (employees)
                         // и его механизм этой аннотации вывести не может
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    /*@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "Employee_University", joinColumns = @JoinColumn(name = "university_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))*/
    //или тут (в этом классе) уже можно было написать просто:
    @ManyToMany (mappedBy = "universities")//, где "universities" это переменная для связи из класса "Employee"
    private Set<Employee> employees;

    @ManyToOne
    @JoinColumn (name = "city_id")
    private City cities;
}
