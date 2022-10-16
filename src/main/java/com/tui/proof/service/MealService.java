package com.tui.proof.service;

import com.tui.proof.dto.meal.MealDTO;
import com.tui.proof.mappers.MealMapper;
import com.tui.proof.model.Meal;
import com.tui.proof.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MealService {

    private MealRepository mealRepository;
    private MealMapper mealMapper;

    @Autowired
    public MealService(MealRepository mealRepository, MealMapper mealMapper) {
        this.mealRepository = mealRepository;
        this.mealMapper = mealMapper;
    }

    public List<MealDTO> getMeals() {
        return mealMapper.mealListToMealDtoList(mealRepository.findAll());
    }

    public MealDTO getMealDto(Long id) {
        return mealMapper.mealToMealDto(mealRepository.findById(id).orElse(null));
    }

    public Meal getMealByCode(String code) {
        return mealRepository.getMealByCode(code).orElse(null);
    }

    public MealDTO getMealDtoByCode(String code) {
        return mealMapper.mealToMealDto(mealRepository.getMealByCode(code).orElse(null));
    }
}
