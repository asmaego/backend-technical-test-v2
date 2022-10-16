package com.tui.proof.mappers;

import com.tui.proof.dto.order.OrderCreateDTO;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.model.Address;
import com.tui.proof.model.Client;
import com.tui.proof.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    OrderItemMapper orderItemMapper;

    public OrderDTO orderToOrderDto(Order order) {
        if (Objects.isNull(order)) {
            return null;
        }

        return OrderDTO.builder()
                .id(order.getId())
                .number(order.getNumber().toString())
                .creationDate(order.getCreationDate())
                .totalPrice(order.getTotalPrice())
                .deliveryAddressId(order.getDeliveryAddress().getId())
                .clientId(order.getClient().getId())
                .orderItems(order.getOrderItems().stream().map(orderItemMapper::orderItemToOrderItemDto).collect(Collectors.toList()))
                .build();
    }

    public List<OrderDTO> orderListToOrderDtoList(List<Order> orders){
        if (Objects.isNull(orders)) {
            return null;
        }

        return orders.stream().map(this::orderToOrderDto).collect(Collectors.toList());
    }

    public Order orderDtoToOrder(OrderDTO orderDto) {
        if (Objects.isNull(orderDto)) {
            return null;
        }

        return Order.builder()
                .id(orderDto.getId())
                .number(UUID.fromString(orderDto.getNumber()))
                .creationDate(orderDto.getCreationDate())
                .totalPrice(orderDto.getTotalPrice())
                .deliveryAddress(new Address(orderDto.getDeliveryAddressId()))
                .client(new Client(orderDto.getClientId()))
                .orderItems(orderDto.getOrderItems().stream().map(orderItemMapper::orderItemDtoToOrderItem).collect(Collectors.toList()))
                .build();
    }

    public OrderDTO orderCreateDtoToOrderDto(OrderCreateDTO orderCreateDTO) {
        if (Objects.isNull(orderCreateDTO)) {
            return null;
        }

        return OrderDTO.builder()
                .deliveryAddressId(orderCreateDTO.getDeliveryAddressId())
                .clientId(orderCreateDTO.getClientId())
                .orderItems(orderCreateDTO.getOrderItems().stream().map(orderItemMapper::orderItemCreateDTOToOrderItem).collect(Collectors.toList()))
                .build();
    }
}
