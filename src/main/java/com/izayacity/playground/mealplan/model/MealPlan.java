package com.izayacity.playground.mealplan.model;

import com.izayacity.playground.mealplan.domain.MealPlanDomain;

import java.util.Comparator;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 *
 * @author francis
 */
public class MealPlan {

    private Integer index;
    private String id;
    private float totalCost;
    private MealModel meal0;
    private MealModel meal1;
    private Nutrition nutrition = new Nutrition();

    public MealPlan() {
    }

    public MealPlan(float totalCost, MealModel meal0, MealModel meal1) {
        this.totalCost = totalCost;
        this.meal0 = new MealModel(meal0);
        this.meal1 = new MealModel(meal1);
        this.id = meal0.getId() + meal1.getId();
    }

    public MealPlan(MealModel meal0, MealModel meal1) {
        this(meal0.getPrice() + meal1.getPrice(), meal0, meal1);
    }

    public static class MealPlanComparator implements Comparator<MealPlan> {

        @Override
        public int compare(MealPlan o1, MealPlan o2) {
            float delta = 0.01f;
            if (Math.abs(o1.getTotalCost() - o2.getTotalCost()) < delta) {
                return 0;
            }
            if (o1.getTotalCost() > o2.getTotalCost()) {
                return -1;
            }
            return 1;
        }
    }

    public boolean checkHealthy() {
        return this.nutrition.getEnergy() <= MealPlanDomain.NutritionIndex.MEDIUM.getValue() * 2 &&
                this.nutrition.getVitamin() >= MealPlanDomain.NutritionIndex.MEDIUM.getValue() * 2 &&
                this.nutrition.getAmount() >= MealPlanDomain.NutritionIndex.MEDIUM.getValue() * 2 &&
                this.nutrition.getAmount() < MealPlanDomain.NutritionIndex.HIGH.getValue() * 2;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public MealModel getMeal0() {
        return meal0;
    }

    public MealModel getMeal1() {
        return meal1;
    }

    public String getId() {
        return id;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void doSwap() {
        MealModel modelTmp = this.meal1;
        this.meal1 = this.meal0;
        this.meal0 = modelTmp;
    }
}
