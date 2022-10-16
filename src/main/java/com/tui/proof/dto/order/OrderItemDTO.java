package com.tui.proof.dto.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Integer quantity;
    private String mealCode;
    private Long orderId;
    private Double itemPrice;
}
