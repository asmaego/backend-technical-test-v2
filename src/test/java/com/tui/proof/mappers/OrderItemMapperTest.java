package com.tui.proof.mappers;

import com.tui.proof.dto.order.OrderItemCreateDTO;
import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.model.Meal;
import com.tui.proof.model.Order;
import com.tui.proof.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest {

    OrderItemMapper orderItemMapper = new OrderItemMapper();

    @Test
    void orderItemToOrderItemDto() {
        Meal meal = new Meal(100L, "0001", "pilotes", 1.3);
        Order order = new Order(10L, UUID.randomUUID(), LocalDateTime.now(), null, null, null, null);
        OrderItem orderItem = new OrderItem(5L, 1, 10.0, meal, order);

        OrderItemDTO orderItemDTO = orderItemMapper.orderItemToOrderItemDto(orderItem);

        assertEquals(10L, orderItemDTO.getOrderId());
        assertEquals(10.0, orderItemDTO.getItemPrice());
        assertEquals(1, orderItemDTO.getQuantity());
        assertEquals("0001", orderItemDTO.getMealCode());
        assertEquals(5L, orderItemDTO.getId());
    }

    @Test
    void orderItemToOrderItemDto_when_null() {
        OrderItemDTO orderItemDTO = orderItemMapper.orderItemToOrderItemDto(null);
        assertNull(orderItemDTO);
    }

    @Test
    void orderItemDtoToOrderItem() {
        OrderItemDTO orderItemDTO = new OrderItemDTO(5L, 1, "0001", 9L, 110.0);

        OrderItem orderItem = orderItemMapper.orderItemDtoToOrderItem(orderItemDTO);

        assertEquals(9L, orderItem.getOrder().getId());
        assertEquals(110.0, orderItem.getItemPrice());
        assertEquals(1, orderItem.getQuantity());
        assertEquals("0001", orderItem.getMeal().getCode());
        assertEquals(5L, orderItem.getId());
    }

    @Test
    void orderItemDtoToOrderItem_when_null() {
        OrderItem orderItem = orderItemMapper.orderItemDtoToOrderItem(null);
        assertNull(orderItem);
    }

    @Test
    void orderItemCreateDTOToOrderItem() {
        OrderItemCreateDTO dto = new OrderItemCreateDTO("0010", 5);

        OrderItemDTO orderItemDTO = orderItemMapper.orderItemCreateDTOToOrderItem(dto);

        assertEquals("0010", orderItemDTO.getMealCode());
        assertEquals(5, orderItemDTO.getQuantity());
    }
}