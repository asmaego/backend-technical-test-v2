package com.tui.proof.dto.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreateDTO {
    private String mealCode;
    private Integer quantity;
}
