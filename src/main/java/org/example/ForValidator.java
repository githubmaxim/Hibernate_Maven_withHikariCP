package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class ForValidator {

    @Positive(message = "a cannot be null")
    private int a;
    private int b;

    public int summ(){
        int c;
        c = a + b;
        return c;
    }
}
