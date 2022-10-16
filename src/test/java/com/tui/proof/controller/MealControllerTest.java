package com.tui.proof.controller;

import com.tui.proof.dto.meal.MealDTO;
import com.tui.proof.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MealControllerTest {

    String rootUrl = "/api/v1/meals/";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private MealService mealService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getMeals() throws Exception {
        when(mealService.getMeals()).thenReturn(List.of(new MealDTO(1L, "0001", "pilotes", 1.3)));
        ResultActions perform = mockMvc.perform(get(rootUrl).contentType(APPLICATION_JSON_UTF8));

        perform.andExpect(status().isOk());
        perform.andExpect(result -> assertEquals("[{\"id\":1,\"code\":\"0001\",\"name\":\"pilotes\",\"price\":1.3}]", result.getResponse().getContentAsString()));
    }

    @Test
    void getMealById() throws Exception {
        when(mealService.getMealDto(any())).thenReturn(new MealDTO(1L, "0001", "pilotes", 1.3));
        ResultActions perform = mockMvc.perform(get(rootUrl + "1").contentType(APPLICATION_JSON_UTF8));

        perform.andExpect(status().isOk());
        perform.andExpect(result -> assertEquals("{\"id\":1,\"code\":\"0001\",\"name\":\"pilotes\",\"price\":1.3}", result.getResponse().getContentAsString()));
    }

    @Test
    void getMealByCode() throws Exception {
        when(mealService.getMealDtoByCode(any())).thenReturn(new MealDTO(1L, "0001", "pilotes", 1.3));
        ResultActions perform = mockMvc.perform(get(rootUrl + "byCode/0001").contentType(APPLICATION_JSON_UTF8));

        perform.andExpect(status().isOk());
        perform.andExpect(result -> assertEquals("{\"id\":1,\"code\":\"0001\",\"name\":\"pilotes\",\"price\":1.3}", result.getResponse().getContentAsString()));
    }
}