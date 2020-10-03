package com.izayacity.playground.mealplan.logic;

import com.google.gson.Gson;
import com.izayacity.algorithms.graph.Graph;
import com.izayacity.algorithms.graph.GraphEdge;
import com.izayacity.playground.mealplan.meta.MealPlanMeta;
import com.izayacity.playground.mealplan.meta.Pricing;
import com.izayacity.playground.mealplan.meta.PricingItem;
import com.izayacity.playground.mealplan.meta.Restaurant;
import com.izayacity.playground.mealplan.model.MealModel;
import com.izayacity.playground.mealplan.model.MealPlan;
import com.izayacity.playground.mealplan.model.Nutrition;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.*;

import static com.izayacity.algorithms.graph.Graph.*;

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
            if (meals.get(i).getPrice() > budget) {
                break;
            }
            items.addFirst(new MealModel(meals.get(i)));
        }
        return items;
    }

    public List<MealPlan> allMealPlans(int budget, int gap, int people) {
        List<MealPlan> mealPlans = new ArrayList<>();
        List<MealModel> meals = this.getMealPlanMeta().getMealInfoList();
        Map<String, Restaurant> restaurantMap = this.getMealPlanMeta().getRestaurantMap();
        meals = this.processPricing(meals, restaurantMap, people);

        Set<String> visited = new HashSet<>();
        allMealPlansUtil(budget, gap, mealPlans, meals, 0, meals.size() - 1, visited);

        List<String[]> mealPlanIds = this.getMealPlanIds(mealPlans);
        String[][] mealIdArrs = new String[mealPlanIds.size()][EDGE_NODES];

        for (int i = 0; i < mealPlanIds.size(); i++) {
            mealIdArrs[i] = mealPlanIds.get(i);
        }
        List<String[]> mstIds = this.mealIdsMst(mealIdArrs);
        List<MealPlan> mstMealPlans = new ArrayList<>();
        Set<String> mealPlanIdSet = new HashSet<>();

        for (String[] mstId : mstIds) {
            String mealId0 = mstId[SRC_NODE];
            String mealId1 = mstId[DEST_NODE];
            mealPlanIdSet.add(mealId0 + mealId1);
            mealPlanIdSet.add(mealId1 + mealId0);
        }
        for (MealPlan mealPlan : mealPlans) {
            if (!mealPlanIdSet.contains(mealPlan.getId())) {
                continue;
            }
            mstMealPlans.add(mealPlan);
        }
        mstMealPlans.sort(new MealPlan.MealPlanComparator());
        return mstMealPlans;
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

    public List<MealModel> processPricing(List<MealModel> meals, Map<String, Restaurant> restaurantMap, int people) {
        for (MealModel mealModel : meals) {
            if (mealModel.getOnSale() != null) {
                float price = (mealModel.getOnSale() + mealModel.getPrice() * (people - 1)) / people;
                mealModel.setPrice(price);
                continue;
            }
            float total = mealModel.getPrice() * people;
            String restaurantId = mealModel.getRestaurantId();
            Pricing pricing = restaurantMap.get(restaurantId).getPricing();

            if (pricing == null || pricing.getItemList() == null) {
                continue;
            }
            for (int i = pricing.getItemList().size() - 1; i >= 0; i--) {
                PricingItem pricingItem = pricing.getItemList().get(i);

                if (total >= pricingItem.getCondition()) {
                    total -= pricingItem.getDiscount();
                    break;
                }
            }
            mealModel.setPrice(total / people);
        }
        meals.sort(new MealModel.MealModelComparator());
        return meals;
    }

    public void display(Object obj) {
        if (obj == null) {
            System.out.println("null");
        } else if (obj instanceof String) {
            System.out.println(obj);
        } else {
            Gson gson = new Gson();
            System.out.println(gson.toJson(obj));
        }
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
        float diff = budget - meals.get(lo).getPrice() - meals.get(hi).getPrice();
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

    public List<String[]> getMealPlanIds(List<MealPlan> mealPlans) {
        List<String[]> mealIdList = new ArrayList<>();

        for (MealPlan mealPlan : mealPlans) {
            mealIdList.add(new String[]{mealPlan.getMeal0().getId(), mealPlan.getMeal1().getId()});
        }
        return mealIdList;
    }

    public List<GraphEdge<String>> generateGraphEdges(String[][] mealIdArr) {
        List<GraphEdge<String>> list = new ArrayList<>();
        Map<String, Integer> weightMap = new HashMap<>();

        for (int i = 0; i < mealIdArr.length - 1; i++) {
            String mealId0 = mealIdArr[i][SRC_NODE];
            String mealId1 = mealIdArr[i][DEST_NODE];
            this.addWeight(weightMap, mealId0);
            this.addWeight(weightMap, mealId1);
        }
        for (int i = 0; i < mealIdArr.length - 1; i++) {
            String mealId0 = mealIdArr[i][SRC_NODE];
            String mealId1 = mealIdArr[i][DEST_NODE];
            list.add(new GraphEdge<>(mealId0, mealId1, weightMap.get(mealId0) + weightMap.get(mealId1)));
        }
        return list;
    }

    public void addWeight(Map<String, Integer> weightMap, String mealId) {
        if (!weightMap.containsKey(mealId)) {
            weightMap.put(mealId, 1);
            return;
        }
        weightMap.put(mealId, 1 + weightMap.get(mealId));
    }

    public List<String[]> mealIdsMst(String[][] mealIdArr) {
        List<String[]> mealIdList = new ArrayList<>();
        List<GraphEdge<String>> graphEdges = this.generateGraphEdges(mealIdArr);
        Graph<String> graph = new Graph<>(graphEdges, true);
        List<Graph.EdgeInfo<String>> mst = graph.kruskalMst();
        display(mst);

        for (Graph.EdgeInfo<String> edgeInfo : mst) {
            mealIdList.add(new String[]{edgeInfo.getSrc(), edgeInfo.getDest()});
        }
        return mealIdList;
    }
}
