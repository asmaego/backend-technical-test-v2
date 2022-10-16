package com.tui.proof.dto.order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String number;
    private LocalDateTime creationDate;
    private Double totalPrice;
    private Long deliveryAddressId;
    private Long clientId;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
}