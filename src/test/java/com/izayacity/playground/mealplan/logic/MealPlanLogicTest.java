package com.izayacity.playground.mealplan.logic;

import com.google.gson.Gson;
import com.izayacity.playground.mealplan.meta.MealPlanMeta;
import com.izayacity.playground.mealplan.model.MealModel;
import com.izayacity.playground.mealplan.model.MealPlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
class MealPlanLogicTest {

    private MealPlanLogic mealPlanLogic;
    private Gson gson;

    @BeforeEach
    void setUp() {
        try {
            this.gson = new Gson();
            this.mealPlanLogic = new MealPlanLogic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void display(Object obj) {
        System.out.println(this.gson.toJson(obj));
    }

    @Test
    void getMealPlanMeta() {
        MealPlanMeta meta = this.mealPlanLogic.getMealPlanMeta();
        Assertions.assertNotNull(meta);
        this.display(meta);
    }

    @Test
    void mealsUnderBudget() {
        List<MealModel> meals = this.mealPlanLogic.mealsUnderBudget(30);
        this.display(meals);
    }

    @Test
    void allMealPlans() {
        List<MealPlan> mealPlans = this.mealPlanLogic.allMealPlans(60, 5);
        this.display(mealPlans);
    }
}
