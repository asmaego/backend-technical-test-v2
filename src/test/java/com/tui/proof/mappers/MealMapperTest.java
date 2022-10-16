package com.tui.proof.mappers;

import com.tui.proof.dto.meal.MealDTO;
import com.tui.proof.model.Meal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealMapperTest {

    MealMapper mealMapper= new MealMapper();

    @Test
    void mealToMealDto_when_not_null() {
        Meal meal = new Meal(1L,"0001","pilotes",1.3);

        MealDTO dto = mealMapper.mealToMealDto(meal);

        assertEquals(1L,dto.getId());
        assertEquals("0001",dto.getCode());
        assertEquals("pilotes",dto.getName());
        assertEquals(1.3,dto.getPrice());
    }

    @Test
    void mealToMealDto_when_null() {
        MealDTO dto = mealMapper.mealToMealDto(null);
        assertNull(dto);
    }

    @Test
    void mealListToMealDtoList_when_not_null() {
        List<Meal> mealList = new ArrayList<>();
        mealList.add(new Meal(1L,"0001","pilotes",1.3));

        List<MealDTO> mealDtoList= mealMapper.mealListToMealDtoList(mealList);

        assertEquals(1,mealDtoList.size());
        assertEquals(1L,mealDtoList.get(0).getId());
        assertEquals("0001",mealDtoList.get(0).getCode());
        assertEquals("pilotes",mealDtoList.get(0).getName());
        assertEquals(1.3,mealDtoList.get(0).getPrice());
    }

    @Test
    void mealListToMealDtoList_when_null() {
        List<MealDTO> mealDtoList= mealMapper.mealListToMealDtoList(null);
        assertNull(mealDtoList);
    }

    @Test
    void mealDtoToMeal_when_not_null() {
        MealDTO dto = new MealDTO(1L,"0001","pilotes",1.3);

        Meal meal = mealMapper.mealDtoToMeal(dto);

        assertEquals(1L,meal.getId());
        assertEquals("0001",meal.getCode());
        assertEquals("pilotes",meal.getName());
        assertEquals(1.3,meal.getPrice());
    }

    @Test
    void mealDtoToMeal_when_null() {
        Meal meal = mealMapper.mealDtoToMeal(null);
        assertNull(meal);
    }
}