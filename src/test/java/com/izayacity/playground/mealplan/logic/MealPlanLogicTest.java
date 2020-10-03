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
        List<MealModel> meals = this.mealPlanLogic.mealsUnderBudget(50);
        this.display(meals);
    }

    @Test
    void allMealPlans() {
        List<MealPlan> mealPlans = this.mealPlanLogic.allMealPlans(84, 8, 5);
        System.out.println("mealPlans:");
        this.display(mealPlans);
    }

    @Test
    void reOrderMealPlanIds() {
        String[][] mealIdArr = new String[][]{{"A", "C"}, {"A", "B"}, {"B", "C"}, {"A", "C"}, {"A", "D"}, {"B", "D"}, {"A", "B"}, {"C", "D"}, {"C", "F"}, {"B", "F"}, {"D", "F"}, {"A", "D"}};
        List<String[]> mealIds = this.mealPlanLogic.mealIdsMst(mealIdArr);
        this.display(mealIds);
    }
}
