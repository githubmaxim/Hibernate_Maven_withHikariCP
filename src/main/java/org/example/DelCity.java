package org.example;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "delcity")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"town"})
@ToString(of = {"town"})
public class DelCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String town;

    @OneToMany (mappedBy = "delcities")
    private Set<DelUniversity> delUniversitySet;
}
