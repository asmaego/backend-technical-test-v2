package com.tui.proof.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tui.proof.dto.login.LoginDTO;
import com.tui.proof.dto.order.OrderCreateDTO;
import com.tui.proof.dto.order.OrderItemCreateDTO;
import com.tui.proof.dto.order.SearchDTO;
import com.tui.proof.model.*;
import com.tui.proof.repository.OrderItemRepository;
import com.tui.proof.repository.OrderRepository;
import com.tui.proof.service.AddressService;
import com.tui.proof.service.ClientService;
import com.tui.proof.service.MealService;
import com.tui.proof.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest {

    String rootUrl = "/api/v1/orders/";
    MockMvc mockMvc;

    UUID rand = UUID.randomUUID();
    MockedStatic<UUID> uuid;

    @MockBean
    OrderRepository orderRepository;
    @MockBean
    OrderItemRepository orderItemRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        uuid = Mockito.mockStatic(UUID.class);
    }

    @AfterEach
    void clear() {
        uuid.close();
    }

    @Test
    void createOrder() throws Exception {
        OrderItemCreateDTO itemCreateDTO = new OrderItemCreateDTO("0001", 5);
        OrderCreateDTO createDTO = new OrderCreateDTO(1L, 1L, List.of(itemCreateDTO));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(createDTO);


        uuid.when(UUID::randomUUID).thenReturn(rand);

        ResultActions perform = mockMvc.perform(post(rootUrl + "create").contentType(APPLICATION_JSON_UTF8).content(requestJson));

        perform.andExpect(status().isCreated());
        perform.andExpect(result -> assertEquals("{\"message\":\"Order has been created, number : " + rand.toString() + "\"}", result.getResponse().getContentAsString()));
    }

    @Test
    void updateOrder() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(1L, 1, 10.0, new Meal(1L, "0001", "pilotes", 1.3), new Order(1L)));
        Order order = new Order(1L, rand, now, 10.9, address, client, orderItems);

        uuid.when(UUID::randomUUID).thenReturn(rand);
        when(orderRepository.findByNumber(any())).thenReturn(Optional.of(order));

        OrderItemCreateDTO itemUpdateDTO = new OrderItemCreateDTO("0001", 15);
        OrderCreateDTO updateDTO = new OrderCreateDTO(1L, 1L, List.of(itemUpdateDTO));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJsonUpdate = ow.writeValueAsString(updateDTO);

        ResultActions perform = mockMvc.perform(put(rootUrl + rand.toString()).contentType(APPLICATION_JSON_UTF8).content(requestJsonUpdate));
        perform.andExpect(status().isOk());
        perform.andExpect(result -> assertEquals("{\"message\":\"Order has been updated\"}", result.getResponse().getContentAsString()));

    }

    @Test
    void searchOrder() throws Exception {
        LocalDateTime now = LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0);
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(1L, 1, 10.0, new Meal(1L, "0001", "pilotes", 1.3), new Order(1L)));
        Order order = new Order(1L, rand, now, 10.9, address, client, orderItems);

        uuid.when(UUID::randomUUID).thenReturn(rand);
        when(orderRepository.findByKeyword(any())).thenReturn(List.of(order));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJsonUpdate = ow.writeValueAsString(new SearchDTO("a"));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", getAuth());
        ResultActions perform = mockMvc.perform(post(rootUrl + "search").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders).content(requestJsonUpdate));
        perform.andExpect(status().isOk());
        perform.andExpect(result -> assertTrue(StringUtils.contains(result.getResponse().getContentAsString(), rand.toString())));
    }

    @Test
    void getOrderByNumber() throws Exception {
        LocalDateTime now = LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0);
        Address address = new Address(1L, "street", "postcode", "city", "country", null);
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(1L, 1, 10.0, new Meal(1L, "0001", "pilotes", 1.3), new Order(1L)));
        Order order = new Order(1L, rand, now, 10.9, address, client, orderItems);

        uuid.when(UUID::randomUUID).thenReturn(rand);
        when(orderRepository.findByNumber(any())).thenReturn(Optional.of(order));

        ResultActions perform = mockMvc.perform(get(rootUrl + "/byNumber/" + rand.toString()).contentType(APPLICATION_JSON_UTF8));
        perform.andExpect(status().isOk());
        perform.andExpect(result -> assertTrue(StringUtils.contains(result.getResponse().getContentAsString(), rand.toString())));
    }

    String getAuth() throws Exception {
        LoginDTO dto = new LoginDTO("admin", "password");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);
        String auth = "Bearer ";

        MvcResult mvcResult = mockMvc.perform(post("/auth/signin").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andReturn();
        auth += mvcResult.getResponse().getContentAsString().split("\"")[3];

        return auth;
    }
}