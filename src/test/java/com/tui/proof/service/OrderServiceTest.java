package com.tui.proof.service;

import com.tui.proof.dto.order.OrderCreateDTO;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.dto.order.OrderItemCreateDTO;
import com.tui.proof.dto.order.SearchDTO;
import com.tui.proof.exception.BusinessException;
import com.tui.proof.mappers.OrderItemMapper;
import com.tui.proof.mappers.OrderMapper;
import com.tui.proof.model.*;
import com.tui.proof.repository.OrderItemRepository;
import com.tui.proof.repository.OrderRepository;
import com.tui.proof.service.validator.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class OrderServiceTest {

    OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private AddressService addressService;
    @Mock
    private ClientService clientService;
    @Mock
    private MealService mealService;
    @Mock
    private OrderValidator orderValidator;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, orderItemRepository, addressService, clientService, mealService, orderValidator, orderMapper, orderItemMapper);
    }


    @Test
    void createOrder() throws BusinessException {
        UUID rand = UUID.randomUUID();
        OrderItemCreateDTO itemCreateDTO = new OrderItemCreateDTO("0010", 5);
        OrderCreateDTO createDTO = new OrderCreateDTO(1L, 11L, List.of(itemCreateDTO));

        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(11L, "fname", "lname", "0123456", "aa@a", null);
        Meal meal = new Meal(1L, "0010", "pilotes", 1.3);

        MockedStatic<UUID> uuid = Mockito.mockStatic(UUID.class);
        uuid.when(UUID::randomUUID).thenReturn(rand);
        when(mealService.getMealByCode("0010")).thenReturn(meal);
        when(clientService.getClient(11L)).thenReturn(client);
        when(addressService.getAddress(1L)).thenReturn(address);

        String orderNumber = orderService.createOrder(createDTO);

        assertEquals(rand.toString(), orderNumber);
    }

    @Test
    void getOrderDto() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(5L, 1, 10.0, new Meal(100L, "0001", "pilotes", 1.3), new Order(11L)));
        Order order = new Order(11L, rand, now, 10.9, address, client, orderItems);

        when(orderRepository.findById(11L)).thenReturn(Optional.of(order));
        OrderDTO orderDTO = orderService.getOrderDto("11");

        assertNotNull(orderDTO);
        assertEquals(11L, orderDTO.getId());
        assertEquals(rand.toString(), orderDTO.getNumber());
        assertEquals(1L, orderDTO.getClientId());
        assertEquals(now, orderDTO.getCreationDate());
        assertEquals(10.9, orderDTO.getTotalPrice());
        assertEquals(1L, orderDTO.getDeliveryAddressId());
        assertEquals(1, orderDTO.getOrderItems().size());

    }

    @Test
    void getOrderByNumber() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(5L, 1, 10.0, new Meal(100L, "0001", "pilotes", 1.3), new Order(11L)));

        when(orderRepository.findByNumber(rand)).thenReturn(Optional.of(new Order(11L, rand, now, 10.9, address, client, orderItems)));
        Order order = orderService.getOrderByNumber(rand.toString());

        assertNotNull(order);
        assertEquals(11L, order.getId());
        assertEquals(rand, order.getNumber());
        assertEquals(1L, order.getClient().getId());
        assertEquals(now, order.getCreationDate());
        assertEquals(10.9, order.getTotalPrice());
        assertEquals(1L, order.getDeliveryAddress().getId());
        assertEquals(1, order.getOrderItems().size());
    }

    @Test
    void getOrderDtoByNumber() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(5L, 1, 10.0, new Meal(100L, "0001", "pilotes", 1.3), new Order(11L)));
        Order order = new Order(11L, rand, now, 10.9, address, client, orderItems);

        when(orderRepository.findById(11L)).thenReturn(Optional.of(order));
        OrderDTO orderDTO = orderService.getOrderDtoByNumber(rand.toString());

        assertNotNull(orderDTO);
        assertEquals(11L, orderDTO.getId());
        assertEquals(rand.toString(), orderDTO.getNumber());
        assertEquals(1L, orderDTO.getClientId());
        assertEquals(now, orderDTO.getCreationDate());
        assertEquals(10.9, orderDTO.getTotalPrice());
        assertEquals(1L, orderDTO.getDeliveryAddressId());
        assertEquals(1, orderDTO.getOrderItems().size());
    }

    @Test
    void updateOrder() {

    }

    @Test
    void searchOrder() {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(5L, 1, 10.0, new Meal(100L, "0001", "pilotes", 1.3), new Order(11L)));
        Order order = new Order(11L, rand, now, 10.9, address, client, orderItems);

        when(orderRepository.findByKeyword(any())).thenReturn(List.of(order));

        List<OrderDTO> dtos = orderService.searchOrder(new SearchDTO("abc"));

        assertEquals(1, dtos.size());
        assertEquals(11L, dtos.get(0).getId());
        assertEquals(rand.toString(), dtos.get(0).getNumber());
        assertEquals(1L, dtos.get(0).getClientId());
        assertEquals(now, dtos.get(0).getCreationDate());
        assertEquals(10.9, dtos.get(0).getTotalPrice());
        assertEquals(1L, dtos.get(0).getDeliveryAddressId());
        assertEquals(1, dtos.get(0).getOrderItems().size());

    }
}