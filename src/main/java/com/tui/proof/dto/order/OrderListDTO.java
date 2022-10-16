package com.tui.proof.dto.order;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO {
    private List<OrderDTO> orders;
}
