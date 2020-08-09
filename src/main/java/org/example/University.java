package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "university")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    /*@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "Employee_University", joinColumns = @JoinColumn(name = "university_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))*/
    //или тут (в этом классе) уже можно было написать просто:
    @ManyToMany (mappedBy = "universities")//, где "universities" это переменная для связи из класса "Employee"
    private List<Employee> employees;
}
