package org.example;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "city")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"town"})
@ToString(of = {"town"})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String town;

    @OneToMany (mappedBy = "cities")
    private Set<University> universitySet;

}
