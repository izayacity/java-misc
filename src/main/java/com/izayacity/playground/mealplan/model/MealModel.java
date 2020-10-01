package com.izayacity.playground.mealplan.model;

import java.util.Comparator;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
public class MealModel {

    private String id;
    private float price;
    private float packaging;
    private String name;
    private String restaurantName;

    public MealModel() {
    }

    public MealModel(String id, float price, float delivery, String name, String restaurantName) {
        this.id = id;
        this.price = price;
        this.packaging = delivery;
        this.name = name;
        this.restaurantName = restaurantName;
    }

    public MealModel(MealModel obj) {
        this.id = obj.getId();
        this.price = obj.getPrice();
        this.packaging = obj.getPackaging();
        this.name = obj.getName();
        this.restaurantName = obj.getRestaurantName();
    }

    public static class MealModelComparator implements Comparator<MealModel> {

        @Override
        public int compare(MealModel o1, MealModel o2) {
            float delta = 0.01f;
            if (Math.abs(o1.getCost() - o2.getCost()) < delta) {
                return 0;
            }
            if (o1.getCost() > o2.getCost()) {
                return 1;
            }
            return -1;
        }
    }

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public float getPackaging() {
        return packaging;
    }

    public String getName() {
        return name;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public float getCost() {
        return this.price + this.packaging;
    }
}
