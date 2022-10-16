package com.tui.proof.mappers;

import com.tui.proof.dto.order.OrderCreateDTO;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.dto.order.OrderItemCreateDTO;
import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    void orderToOrderDto() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(5L, 1, 10.0, new Meal(100L, "0001", "pilotes", 1.3), new Order(10L)));
        Order order = new Order(10L, rand, now, 10.9, address, client, orderItems);

        OrderDTO dto = orderMapper.orderToOrderDto(order);

        assertEquals(10L, dto.getId());
        assertEquals(rand.toString(), dto.getNumber());
        assertEquals(1L, dto.getClientId());
        assertEquals(now, dto.getCreationDate());
        assertEquals(10.9, dto.getTotalPrice());
        assertEquals(1L, dto.getDeliveryAddressId());
        assertEquals(1, dto.getOrderItems().size());
    }

    @Test
    void orderListToOrderDtoList() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(5L, 1, 10.0, new Meal(100L, "0001", "pilotes", 1.3), new Order(10L)));
        Order order = new Order(10L, rand, now, 10.9, address, client, orderItems);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        List<OrderDTO> dtos = orderMapper.orderListToOrderDtoList(orders);

        assertEquals(1, dtos.size());
        assertEquals(10L, dtos.get(0).getId());
        assertEquals(rand.toString(), dtos.get(0).getNumber());
        assertEquals(1L, dtos.get(0).getClientId());
        assertEquals(now, dtos.get(0).getCreationDate());
        assertEquals(10.9, dtos.get(0).getTotalPrice());
        assertEquals(1L, dtos.get(0).getDeliveryAddressId());
        assertEquals(1, dtos.get(0).getOrderItems().size());
    }

    @Test
    void orderDtoToOrder() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0001", 9L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 1L, 1L, orderItemsDtos);

        Order order = orderMapper.orderDtoToOrder(dto);

        assertEquals(10L, order.getId());
        assertEquals(rand, order.getNumber());
        assertEquals(1L, order.getClient().getId());
        assertEquals(now, order.getCreationDate());
        assertEquals(10.9, order.getTotalPrice());
        assertEquals(1L, order.getDeliveryAddress().getId());
        assertEquals(1, order.getOrderItems().size());
    }

    @Test
    void orderCreateDtoToOrderDto() {
        OrderItemCreateDTO itemCreateDTO = new OrderItemCreateDTO("0010", 5);
        OrderCreateDTO createDTO = new OrderCreateDTO(11L, 111L, List.of(itemCreateDTO));

        OrderDTO dto = orderMapper.orderCreateDtoToOrderDto(createDTO);

        assertEquals(111L, dto.getClientId());
        assertEquals(11L, dto.getDeliveryAddressId());
        assertEquals(1, dto.getOrderItems().size());
    }

    @Test
    void orderMapping_when_null() {
        assertNull(orderMapper.orderToOrderDto(null));
        assertNull(orderMapper.orderListToOrderDtoList(null));
        assertNull(orderMapper.orderDtoToOrder(null));
        assertNull(orderMapper.orderCreateDtoToOrderDto(null));
    }
}