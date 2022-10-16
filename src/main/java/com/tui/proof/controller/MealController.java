package com.tui.proof.controller;

import com.tui.proof.dto.meal.MealDTO;
import com.tui.proof.exception.BusinessException;
import com.tui.proof.service.MealService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("${com.tui.api}${com.tui.api.meals}")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping(path = "")
    public ResponseEntity<List<MealDTO>> getMeals() {
        List<MealDTO> meals = mealService.getMeals();
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MealDTO> getMealById(@NonNull @PathVariable(value = "id") Long id) throws BusinessException {
        MealDTO mealDTO = mealService.getMealDto(id);
        if(Objects.isNull(mealDTO)){
            throw new BusinessException("Meal not found on :: " + id);
        }
        return new ResponseEntity<>(mealDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/byCode/{code}")
    public ResponseEntity<MealDTO> getMealByCode(@NonNull @PathVariable(value = "code") String code) throws BusinessException {
        MealDTO mealDTO = mealService.getMealDtoByCode(code);
        if(Objects.isNull(mealDTO)){
            throw new BusinessException("Meal not found on :: " + code);
        }
        return new ResponseEntity<>(mealDTO, HttpStatus.OK);
    }
}
