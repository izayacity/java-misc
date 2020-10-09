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
 *
 * @author francis
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

    public String[][] arrListToArr(List<String[]> arrList) {
        String[][] arr = new String[arrList.size()][];

        for (int i = 0; i < arrList.size(); i++) {
            arr[i] = arrList.get(i);
        }
        return arr;
    }

    public List<MealPlan> allMealPlans(int budget, int gap, int people) {
        List<MealPlan> mealPlans = new ArrayList<>();
        List<MealModel> meals = this.getMealPlanMeta().getMealInfoList();
        Map<String, Restaurant> restaurantMap = this.getMealPlanMeta().getRestaurantMap();
        meals = this.processPricing(meals, restaurantMap, people);

        allMealPlansUtil(budget, gap, mealPlans, meals, 0, meals.size() - 1, new HashSet<>());
        List<String[]> mealPlanIds = this.getMealPlanIds(mealPlans);
        String[][] mealIdArrs = this.arrListToArr(mealPlanIds);

        List<String[]> mstIdList = this.mealIdsMst(mealIdArrs);
        String[][] mstIdArr = this.arrListToArr(mstIdList);
        List<String[]> restaurantIdArrList = this.restaurantIdFromMealIds(mstIdArr);
        String[][] restaurantIdArr = this.arrListToArr(restaurantIdArrList);
        Map<String, Integer> ridWeight = this.weightMap(restaurantIdArr);

        List<MealPlan> mstMealPlans = this.filterMstMealPlans(mealPlans, mstIdList);
        mstMealPlans.sort((m1, m2) -> (
                ridWeight.get(m1.getMeal0().getRestaurantId()) + ridWeight.get(m1.getMeal1().getRestaurantId())
                        - ridWeight.get(m2.getMeal0().getRestaurantId()) - ridWeight.get(m2.getMeal1().getRestaurantId())
        ));
        List<MealPlan> reOrderedMealPlans = this.reOrderMealPlans(mstMealPlans);

        for (int i = 0; i < reOrderedMealPlans.size(); i++) {
            MealPlan mealPlan = reOrderedMealPlans.get(i);
            mealPlan.setIndex(i);
            String mealId0 = mealPlan.getMeal0().getId();
            String mealId1 = mealPlan.getMeal1().getId();

            if (this.mealPlanMeta.getMealMap().get(mealId0).getAmount() < this.mealPlanMeta.getMealMap().get(mealId1).getAmount()) {
                mealPlan.doSwap();
            }
        }
        return reOrderedMealPlans;
    }

    public List<MealPlan> filterMstMealPlans(List<MealPlan> mealPlans, List<String[]> mstIds) {
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
                this.mealPlanMeta.getMealMap().get(mealId0).getStaple() == this.mealPlanMeta.getMealMap().get(mealId1).getStaple() ||
                this.mealPlanMeta.getMealInfoMap().get(mealId0).getRestaurantId().equals(this.mealPlanMeta.getMealInfoMap().get(mealId1).getRestaurantId());
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
        Map<String, Integer> weightMap = this.weightMap(mealIdArr);

        for (int i = 0; i < mealIdArr.length; i++) {
            String mealId0 = mealIdArr[i][SRC_NODE];
            String mealId1 = mealIdArr[i][DEST_NODE];
            list.add(new GraphEdge<>(mealId0, mealId1, weightMap.get(mealId0) + weightMap.get(mealId1)));
        }
        return list;
    }

    public Map<String, Integer> weightMap(String[][] arr) {
        Map<String, Integer> weightMap = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            this.addWeight(weightMap, arr[i][SRC_NODE]);
            this.addWeight(weightMap, arr[i][DEST_NODE]);
        }
        return weightMap;
    }

    public List<String[]> restaurantIdFromMealIds(String[][] mealIdArr) {
        List<String[]> list = new ArrayList<>();
        Map<String, MealModel> mealInfoMap = this.getMealPlanMeta().getMealInfoMap();

        for (int i = 0; i < mealIdArr.length; i++) {
            String mealId0 = mealIdArr[i][SRC_NODE];
            String mealId1 = mealIdArr[i][DEST_NODE];
            String rid0 = mealInfoMap.get(mealId0).getRestaurantId();
            String rid1 = mealInfoMap.get(mealId1).getRestaurantId();
            list.add(new String[]{rid0, rid1});
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

        for (Graph.EdgeInfo<String> edgeInfo : mst) {
            mealIdList.add(new String[]{edgeInfo.getSrc(), edgeInfo.getDest()});
        }
        return mealIdList;
    }

    public List<MealPlan> reOrderMealPlans(List<MealPlan> mealPlans) {
        List<String> prevList = new ArrayList<>();

        for (int i = 0; i < mealPlans.size(); i++) {
            String rid0 = mealPlans.get(i).getMeal0().getRestaurantId();
            String rid1 = mealPlans.get(i).getMeal1().getRestaurantId();

            if (prevList.isEmpty()) {
                prevList.add(rid0);
                prevList.add(rid1);
                continue;
            }
            Set<String> tmpSet = new HashSet<>();
            tmpSet.add(prevList.get(prevList.size() - 1));
            tmpSet.add(prevList.get(prevList.size() - 2));

            if (tmpSet.contains(rid0) || tmpSet.contains(rid1)) {
                int index = this.findSwapIndex(mealPlans, i + 1, tmpSet);
                if (index == -1) {
                    return mealPlans.subList(0, i);
                }
                this.listSwap(mealPlans, i, index);
            } else if (prevList.size() > 2) {
                tmpSet.addAll(prevList);

                if (tmpSet.contains(rid0) && tmpSet.contains(rid1)) {
                    int index = this.findSwapIndex(mealPlans, i + 1,
                            new HashSet<>(Arrays.asList(prevList.get(0), prevList.get(2), prevList.get(3))));
                    if (index == -1) {
                        index = this.findSwapIndex(mealPlans, i + 1,
                                new HashSet<>(Arrays.asList(prevList.get(1), prevList.get(2), prevList.get(3))));
                    }
                    if (index == -1) {
                        return mealPlans.subList(0, i);
                    }
                    this.listSwap(mealPlans, i, index);
                }
            }
            rid0 = mealPlans.get(i).getMeal0().getRestaurantId();
            rid1 = mealPlans.get(i).getMeal1().getRestaurantId();

            if (prevList.size() == 2) {
                prevList.add(rid0);
                prevList.add(rid1);
            } else {
                prevList.set(0, prevList.get(2));
                prevList.set(1, prevList.get(3));
                prevList.set(2, rid0);
                prevList.set(3, rid1);
            }
        }
        return mealPlans;
    }

    public int findSwapIndex(List<MealPlan> mealPlans, int startIndex, Set<String> ridSet) {
        if (startIndex >= mealPlans.size()) {
            return -1;
        }
        for (int i = startIndex; i < mealPlans.size(); i++) {
            String rid0 = mealPlans.get(i).getMeal0().getRestaurantId();
            String rid1 = mealPlans.get(i).getMeal1().getRestaurantId();

            if (!ridSet.contains(rid0) && !ridSet.contains(rid1)) {
                return i;
            }
        }
        return -1;
    }

    public <T> List<T> listSwap(List<T> list, int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
        return list;
    }

    public MealPlanStats mealPlansStats(List<MealPlan> mealPlans) {
        MealPlanStats stats = new MealPlanStats();

        List<String[]> mealPlanIds = this.getMealPlanIds(mealPlans);
        String[][] mstIdArr = this.arrListToArr(mealPlanIds);
        List<String[]> restaurantIdArrList = this.restaurantIdFromMealIds(mstIdArr);
        String[][] restaurantIdArr = this.arrListToArr(restaurantIdArrList);
        Map<String, Integer> ridWeight = this.weightMap(restaurantIdArr);

        List<MealPlanWeight> weightList = new ArrayList<>();
        int total = 0;

        for (Map.Entry<String, Integer> entry : ridWeight.entrySet()) {
            String rid = entry.getKey();
            Integer weight = entry.getValue();
            total += weight;

            String name = this.getMealPlanMeta().getRestaurantMap().get(rid).getName();
            weightList.add(new MealPlanWeight(rid, name, weight));
        }
        weightList.sort(Comparator.comparing(o -> o.restaurantId));
        stats.restaurants = weightList;
        stats.total = total;
        return stats;
    }

    public static class MealPlanStats {
        public List<MealPlanWeight> restaurants;
        public Integer total;
    }

    public static class MealPlanWeight {
        public String restaurantId;
        public String name;
        public Integer weight;

        public MealPlanWeight(String restaurantId, String name, Integer weight) {
            this.restaurantId = restaurantId;
            this.name = name;
            this.weight = weight;
        }
    }
}
