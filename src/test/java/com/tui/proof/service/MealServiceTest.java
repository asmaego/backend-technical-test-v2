package com.tui.proof.service;

import com.tui.proof.dto.meal.MealDTO;
import com.tui.proof.mappers.MealMapper;
import com.tui.proof.model.Meal;
import com.tui.proof.repository.ClientRepository;
import com.tui.proof.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class MealServiceTest {

    MealService mealService;
    @Mock
    MealRepository mealRepository;
    @Autowired
    MealMapper mealMapper;

    @BeforeEach
    void setUp() {
        mealService = new MealService(mealRepository, mealMapper);
    }

    @Test
    void getMeals() {
        when(mealRepository.findAll()).thenReturn(List.of(
                new Meal(1L, "0001", "pilotes", 1.3),
                new Meal(2L, "0002", "pizza", 2.99)
        ));

        List<MealDTO> mealDTOS = mealService.getMeals();

        assertEquals(2, mealDTOS.size());
        assertEquals(1L, mealDTOS.get(0).getId());
        assertEquals("0001", mealDTOS.get(0).getCode());
        assertEquals("pilotes", mealDTOS.get(0).getName());
        assertEquals(1.3, mealDTOS.get(0).getPrice());
    }

    @Test
    void getMealDto() {
        when(mealRepository.findById(any())).thenReturn(Optional.of(new Meal(11L, "0011", "pilotes", 1.3)));

        MealDTO mealDTO = mealService.getMealDto(11L);

        assertNotNull(mealDTO);
        assertEquals(11L, mealDTO.getId());
        assertEquals("0011", mealDTO.getCode());
        assertEquals("pilotes", mealDTO.getName());
        assertEquals(1.3, mealDTO.getPrice());
    }

    @Test
    void getMealByCode() {
        when(mealRepository.getMealByCode(any())).thenReturn(Optional.of(new Meal(11L, "0011", "pilotes", 1.3)));

        Meal meal = mealService.getMealByCode("0011");

        assertNotNull(meal);
        assertEquals(11L, meal.getId());
        assertEquals("0011", meal.getCode());
        assertEquals("pilotes", meal.getName());
        assertEquals(1.3, meal.getPrice());
    }

    @Test
    void getMealDtoByCode() {
        when(mealRepository.getMealByCode(any())).thenReturn(Optional.of(new Meal(11L, "0011", "pilotes", 1.3)));

        MealDTO mealDTO = mealService.getMealDtoByCode("0011");

        assertNotNull(mealDTO);
        assertEquals(11L, mealDTO.getId());
        assertEquals("0011", mealDTO.getCode());
        assertEquals("pilotes", mealDTO.getName());
        assertEquals(1.3, mealDTO.getPrice());
    }
}