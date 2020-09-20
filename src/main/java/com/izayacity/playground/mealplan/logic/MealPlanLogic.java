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
        String prefix = "src/main/java/com/izayacity/playground/mealplan/configs/";
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

    public List<MealModel> mealsUnderBudget(int budget) {
        LinkedList<MealModel> items = new LinkedList<>();
        List<MealModel> meals = this.getMealPlanMeta().getMealInfoList();
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getCost() > budget) {
                break;
            }
            items.addFirst(new MealModel(meals.get(i)));
        }
        return items;
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
                this.mealPlanMeta.getMealMap().get(meal0.getId()).getVitamin() + this.mealPlanMeta.getMealMap().get(meal1.getId()).getVitamin(),
                this.mealPlanMeta.getMealMap().get(meal0.getId()).getAmount() + this.mealPlanMeta.getMealMap().get(meal1.getId()).getAmount()
        );
        mealPlan.setNutrition(nutrition);
        return mealPlan;
    }

    public boolean checkAvailability(String mealId) {
        return this.mealPlanMeta.getMealMap().get(mealId).getWeight() > 0;
    }

    public boolean checkAlike(String mealId0, String mealId1) {
        return this.mealPlanMeta.getMealMap().get(mealId0).getCategory() == this.mealPlanMeta.getMealMap().get(mealId1).getCategory() ||
                this.mealPlanMeta.getMealMap().get(mealId0).getStaple() == this.mealPlanMeta.getMealMap().get(mealId1).getStaple();
    }

    public void allMealPlansUtil(int budget, int gap, List<MealPlan> mealPlans, List<MealModel> meals, int lo, int hi, Set<String> visited) {
        String hKey = meals.get(lo).getId() + meals.get(hi).getId();
        if (lo >= hi || visited.contains(hKey)) {
            return;
        }
        visited.add(hKey);
        float diff = budget - meals.get(lo).getCost() - meals.get(hi).getCost();
        if (diff < 0) {
            allMealPlansUtil(budget, gap, mealPlans, meals, lo, hi - 1, visited);
        } else if (diff > gap) {
            allMealPlansUtil(budget, gap, mealPlans, meals, lo + 1, hi, visited);
        } else {
            MealPlan mealPlan = this.makeMealPlan(meals.get(lo), meals.get(hi));
            String mealId0 = meals.get(lo).getId();
            String mealId1 = meals.get(hi).getId();

            if (mealPlan.checkHealthy() && !this.checkAlike(mealId0, mealId1) &&
                    this.checkAvailability(mealId0) && this.checkAvailability(mealId1)) {
                mealPlans.add(mealPlan);
            }
            allMealPlansUtil(budget, gap, mealPlans, meals, lo, hi - 1, visited);
            allMealPlansUtil(budget, gap, mealPlans, meals, lo + 1, hi, visited);
        }
    }
}
