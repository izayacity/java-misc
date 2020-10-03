package com.izayacity.playground.mealplan.meta;

import com.izayacity.playground.mealplan.model.MealModel;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
@Root(name = "root")
public class MealPlanMeta {

    @Attribute
    private int version;

    @Element
    private Resources resources;

    @Element(required = false)
    private FoodAttributes foodAttributes;

    private List<MealModel> mealInfoList;
    private Map<String, Meal> mealMap;
    private Map<String, Restaurant> restaurantMap;
    private Map<String, MealModel> mealInfoMap;

    public MealPlanMeta() {
    }

    @Commit
    public void commit() {
        this.mealInfoList = new ArrayList<>();
        this.mealMap = new HashMap<>();
        this.restaurantMap = new HashMap<>();
        this.mealInfoMap = new HashMap<>();

        for (App app : this.getResources().getAppList()) {
            for (Restaurant restaurant : app.getRestaurantList()) {
                if (restaurant.getDisabled() == 1) {
                    continue;
                }
                String restaurantId = (char)('A' + app.getIndex()) + (restaurant.getIndex() < 10 ? "0" : "") + restaurant.getIndex();
                this.restaurantMap.put(restaurantId, restaurant);

                for (Meal meal : restaurant.getMeals().getItemList()) {
                    if (meal.getDisabled() == 1) {
                        continue;
                    }
                    Float onSale = meal.getOnSale();
                    if (onSale != null) {
                        onSale += restaurant.getPackaging();
                    }
                    MealModel mealModel = new MealModel(
                            meal.getId(),
                            meal.getPrice() + restaurant.getPackaging(),
                            meal.getName(),
                            restaurantId,
                            onSale,
                            restaurant.getName()
                    );
                    this.mealInfoList.add(mealModel);
                    this.mealMap.put(meal.getId(), new Meal(meal));
                    this.mealInfoMap.put(mealModel.getId(), new MealModel(mealModel));
                }
            }
        }
        this.mealInfoList.sort(new MealModel.MealModelComparator());
    }

    public int getVersion() {
        return version;
    }

    public Resources getResources() {
        return resources;
    }

    public FoodAttributes getFoodAttributes() {
        return foodAttributes;
    }

    public List<MealModel> getMealInfoList() {
        return mealInfoList;
    }

    public Map<String, Meal> getMealMap() {
        return mealMap;
    }

    public Map<String, Restaurant> getRestaurantMap() {
        return restaurantMap;
    }

    public Map<String, MealModel> getMealInfoMap() {
        return mealInfoMap;
    }
}
