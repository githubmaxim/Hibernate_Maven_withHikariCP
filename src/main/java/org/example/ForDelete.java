package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "forDelete")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ForDelete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int    id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

}
