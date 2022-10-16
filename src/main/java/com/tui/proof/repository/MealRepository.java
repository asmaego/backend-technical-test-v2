package com.tui.proof.repository;

import com.tui.proof.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> getMealByCode(String code);

    Boolean existsByCode(String code);
}
