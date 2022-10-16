package com.tui.proof.mappers;

import com.tui.proof.dto.order.OrderItemCreateDTO;
import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.model.Meal;
import com.tui.proof.model.Order;
import com.tui.proof.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderItemMapper {

    public OrderItemDTO orderItemToOrderItemDto(OrderItem orderItem) {
        if (Objects.isNull(orderItem)) {
            return null;
        }

        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .mealCode(orderItem.getMeal().getCode())
                .quantity(orderItem.getQuantity())
                .itemPrice(orderItem.getItemPrice())
                .build();
    }

    public OrderItem orderItemDtoToOrderItem(OrderItemDTO orderItemDTO) {
        if (Objects.isNull(orderItemDTO)) {
            return null;
        }

        return OrderItem.builder()
                .id(orderItemDTO.getId())
                .quantity(orderItemDTO.getQuantity())
                .itemPrice(orderItemDTO.getItemPrice())
                .meal(new Meal(orderItemDTO.getMealCode()))
                .order(new Order(orderItemDTO.getOrderId()))
                .build();
    }

    public OrderItemDTO orderItemCreateDTOToOrderItem(OrderItemCreateDTO orderItemCreateDTO) {
        if (Objects.isNull(orderItemCreateDTO)) {
            return null;
        }

        return OrderItemDTO.builder()
                .quantity(orderItemCreateDTO.getQuantity())
                .mealCode(orderItemCreateDTO.getMealCode())
                .build();
    }

}
