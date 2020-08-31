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
@EqualsAndHashCode(of = {"name", "cities"})//в файле City.java вывод поля ссылающегося на University.java не прописываю, иначе зациклится
@ToString(of = {"name", "cities"}) //для отображения в ToString() нужных полей "(of ="
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
    private List<Employee> employees;

    @ManyToOne
    @JoinColumn (name = "city_id")
    private City cities;
}
