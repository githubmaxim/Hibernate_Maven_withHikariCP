package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class ForDelete2 {

    private int a;

    @Positive(message = "b cannot be null")
    private int b;

    public int summ(){
        int c;
        c = a + b;
        return c;
    }
}