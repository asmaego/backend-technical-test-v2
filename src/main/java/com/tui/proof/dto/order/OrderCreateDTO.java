package com.tui.proof.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {
    private Long deliveryAddressId;
    private Long clientId;
    private List<OrderItemCreateDTO> orderItems;
}
