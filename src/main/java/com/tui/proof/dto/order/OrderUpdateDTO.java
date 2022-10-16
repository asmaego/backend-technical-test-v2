package com.tui.proof.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {
    private String number;
    private Long deliveryAddressId;
    private Long clientId;
    private List<OrderItemCreateDTO> orderItems;
}
