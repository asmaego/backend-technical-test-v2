package com.tui.proof.mappers;

import com.tui.proof.dto.meal.MealDTO;
import com.tui.proof.model.Meal;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MealMapper {

    public MealDTO mealToMealDto(Meal meal) {
        if (Objects.isNull(meal)) {
            return null;
        }
        return MealDTO.builder()
                .id(meal.getId())
                .code(meal.getCode())
                .name(meal.getName())
                .price(meal.getPrice())
                .build();

    }

    public List<MealDTO> mealListToMealDtoList(List<Meal> meals) {
        if (Objects.isNull(meals)) {
            return null;
        }

        return meals.stream().map(this::mealToMealDto).collect(Collectors.toList());
    }

    public Meal mealDtoToMeal(MealDTO mealDto) {
        if (Objects.isNull(mealDto)) {
            return null;
        }

        return Meal.builder()
                .id(mealDto.getId())
                .code(mealDto.getCode())
                .name(mealDto.getName())
                .price(mealDto.getPrice())
                .build();
    }
}
