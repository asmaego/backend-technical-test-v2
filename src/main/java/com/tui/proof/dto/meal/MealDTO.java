package com.tui.proof.dto.meal;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDTO {

    private Long id;
    private String code;
    private String name;
    private Double price;
}
