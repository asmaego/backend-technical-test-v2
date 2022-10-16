package com.tui.proof.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "meals")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meal {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String code;

    private String name;

    private Double price;

    public Meal(String code) {
        this.code = code;
    }

    public Meal(Long id) {
        this.id = id;
    }

}
