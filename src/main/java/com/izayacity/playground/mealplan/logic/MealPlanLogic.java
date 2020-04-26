package com.izayacity.playground.mealplan.logic;

import com.izayacity.playground.mealplan.meta.MealPlanMeta;
import com.izayacity.playground.mealplan.model.MealModel;
import com.izayacity.playground.mealplan.model.MealPlan;
import com.izayacity.playground.mealplan.model.Nutrition;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.*;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
public class MealPlanLogic {

    private MealPlanMeta mealPlanMeta;

    public MealPlanLogic() throws Exception {
        String path = this.getMetaPath();
        this.initMealPlanMeta(path);
    }

    public String getMetaPath() {
        String prefix = "src/main/java/com/izayacity/playground/mealplan/";
        return prefix + "mealPlanMeta.xml";
    }

    public void initMealPlanMeta(String path) throws Exception {
        Serializer serializer = new Persister();
        File source = new File(path);
        this.mealPlanMeta = serializer.read(MealPlanMeta.class, source);
    }

    public MealPlanMeta getMealPlanMeta() {
        return mealPlanMeta;
    }

    public List<MealPlan> allMealPlans(int budget, int gap) {
        List<MealPlan> mealPlans = new ArrayList<>();
        List<MealModel> meals = this.getMealPlanMeta().getMealInfoList();
        Set<String> visited = new HashSet<>();
        allMealPlansUtil(budget, gap, mealPlans, meals, 0, meals.size() - 1, visited);
        mealPlans.sort(new MealPlan.MealPlanComparator());
        return mealPlans;
    }

    public MealPlan makeMealPlan(MealModel meal0, MealModel meal1) {
        MealPlan mealPlan = new MealPlan(meal0, meal1);
        Nutrition nutrition = new Nutrition(
                this.mealPlanMeta.getMealMap().get(meal0.getId()).getEnergy() + this.mealPlanMeta.getMealMap().get(meal1.getId()).getEnergy(),
                this.mealPlanMeta.getMealMap().get(meal0.getId()).getProtein() + this.mealPlanMeta.getMealMap().get(meal1.getId()).getProtein(),
                this.mealPlanMeta.getMealMap().get(meal0.getId()).getVitamin() + this.mealPlanMeta.getMealMap().get(meal1.getId()).getVitamin()
        );
        mealPlan.setNutrition(nutrition);
        return mealPlan;
    }

    public void allMealPlansUtil(int budget, int gap, List<MealPlan> mealPlans, List<MealModel> meals, int lo, int hi, Set<String> visited) {
        String hKey = meals.get(lo).getId() + meals.get(hi).getId();
        if (lo >= hi || visited.contains(hKey)) {
            return;
        }
        visited.add(hKey);
        float costs = meals.get(lo).getCost() + meals.get(hi).getCost();
        float diff = budget - meals.get(lo).getCost() - meals.get(hi).getCost();
        if (diff < 0) {
            allMealPlansUtil(budget, gap, mealPlans, meals, lo, --hi, visited);
        } else if (diff > gap) {
            allMealPlansUtil(budget, gap, mealPlans, meals, ++lo, hi, visited);
        } else {
            MealPlan mealPlan = this.makeMealPlan(meals.get(lo), meals.get(hi));
            mealPlans.add(mealPlan);
            allMealPlansUtil(budget, gap, mealPlans, meals, lo, --hi, visited);
            allMealPlansUtil(budget, gap, mealPlans, meals, ++lo, hi, visited);
        }
    }
}
