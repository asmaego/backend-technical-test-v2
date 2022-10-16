package com.tui.proof.service.validator;

import com.tui.proof.dto.order.OrderCreateDTO;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.dto.order.OrderItemCreateDTO;
import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.exception.BusinessException;
import com.tui.proof.model.*;
import com.tui.proof.service.AddressService;
import com.tui.proof.service.ClientService;
import com.tui.proof.service.MealService;
import com.tui.proof.service.OrderService;
import com.tui.proof.service.validator.custom.order.CustomOrderValidator;
import com.tui.proof.service.validator.custom.orderItem.CustomOrderItemValidator;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class OrderValidatorTest {

    @Autowired
    Map<String, CustomOrderValidator> customOrderValidatorMap;
    @Autowired
    Map<String, CustomOrderItemValidator> customOrderItemValidatorMap;
    OrderValidator orderValidator;
    @Mock
    private MealService mealService;
    @Mock
    private ClientService clientService;
    @Mock
    private AddressService addressService;
    @Mock
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderValidator = new OrderValidator(customOrderItemValidatorMap, customOrderValidatorMap, mealService, clientService, addressService, orderService);
    }

    @Test
    void validateCreation() throws BusinessException {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0010", 10L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 11L, 111L, orderItemsDtos);

        Address address = new Address(11L, "street", "postcode", "city", "country", null);
        Client client = new Client(111L, "fname", "lname", "0123456", "aa@a", null);
        Meal meal = new Meal(1L, "0010", "pilotes", 1.3);

        when(mealService.getMealByCode("0010")).thenReturn(meal);
        when(clientService.getClient(111L)).thenReturn(client);
        when(addressService.getAddress(11L)).thenReturn(address);

        orderValidator.validateCreation(dto);
    }

    @Test
    void validateCreation_not_a_valid_meal() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0010", 10L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 11L, 111L, orderItemsDtos);

        Address address = new Address(11L, "street", "postcode", "city", "country", null);
        Client client = new Client(111L, "fname", "lname", "0123456", "aa@a", null);
        Meal meal = new Meal(1L, "0010", "pilotes", 1.3);

        when(mealService.getMealByCode(any())).thenReturn(null);
        when(clientService.getClient(111L)).thenReturn(client);
        when(addressService.getAddress(11L)).thenReturn(address);

        Throwable exception = assertThrows(BusinessException.class, () -> orderValidator.validateCreation(dto));
        assertEquals("Meal with code : " + "0010" + "does not exist", exception.getMessage());
    }

    @Test
    void validateCreation_not_a_valid_client() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0010", 10L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 11L, 111L, orderItemsDtos);

        Address address = new Address(11L, "street", "postcode", "city", "country", null);
        Client client = new Client(111L, "fname", "lname", "0123456", "aa@a", null);
        Meal meal = new Meal(1L, "0010", "pilotes", 1.3);

        when(mealService.getMealByCode("0010")).thenReturn(meal);
        when(clientService.getClient(any())).thenReturn(null);
        when(addressService.getAddress(11L)).thenReturn(address);

        Throwable exception = assertThrows(BusinessException.class, () -> orderValidator.validateCreation(dto));
        assertEquals("Client is not valid", exception.getMessage());
    }

    @Test
    void validateCreation_not_a_valid_address() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0010", 10L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 11L, 111L, orderItemsDtos);

        Address address = new Address(11L, "street", "postcode", "city", "country", null);
        Client client = new Client(111L, "fname", "lname", "0123456", "aa@a", null);
        Meal meal = new Meal(1L, "0010", "pilotes", 1.3);

        when(mealService.getMealByCode("0010")).thenReturn(meal);
        when(clientService.getClient(111L)).thenReturn(client);
        when(addressService.getAddress(any())).thenReturn(null);

        Throwable exception = assertThrows(BusinessException.class, () -> orderValidator.validateCreation(dto));
        assertEquals("Address is not valid", exception.getMessage());
    }

    @Test
    void validateUpdate() throws BusinessException {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0010", 10L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 11L, 111L, orderItemsDtos);

        Address address = new Address(11L, "street", "postcode", "city", "country", null);
        Client client = new Client(111L, "fname", "lname", "0123456", "aa@a", null);
        Meal meal = new Meal(1L, "0010", "pilotes", 1.3);
        Order order = new Order(
                10L, rand, now, 10.9, address, client,
                List.of(new OrderItem(5L, 1, 10.0, new Meal(100L, "0001", "pilotes", 1.3), new Order(10L)))
        );

        when(mealService.getMealByCode("0010")).thenReturn(meal);
        when(clientService.getClient(111L)).thenReturn(client);
        when(addressService.getAddress(11L)).thenReturn(address);
        when(orderService.getOrderByNumber(rand.toString())).thenReturn(order);
        when(orderService.getOrderDtoByNumber(rand.toString())).thenReturn(dto);

        orderValidator.validateUpdate(rand.toString(), dto);
    }

    @Test
    void validateUpdate_not_a_valid_order_number() throws BusinessException {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0010", 10L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 11L, 111L, orderItemsDtos);

        Address address = new Address(11L, "street", "postcode", "city", "country", null);
        Client client = new Client(111L, "fname", "lname", "0123456", "aa@a", null);
        Meal meal = new Meal(1L, "0010", "pilotes", 1.3);

        when(mealService.getMealByCode("0010")).thenReturn(meal);
        when(clientService.getClient(111L)).thenReturn(client);
        when(addressService.getAddress(11L)).thenReturn(address);
        when(orderService.getOrderByNumber(any())).thenReturn(null);

        Throwable exception = assertThrows(BusinessException.class, () -> orderValidator.validateUpdate(rand.toString(), dto));
        assertEquals("Order does not exist", exception.getMessage());
    }
}