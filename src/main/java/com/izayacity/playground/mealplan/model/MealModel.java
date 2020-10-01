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
    private String name;
    private String restaurantId;
    private Float onSale;

    private String restaurantName;

    public MealModel() {
    }

    public MealModel(String id, float price, String name, String restaurantId, Float onSale, String restaurantName) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.restaurantId = restaurantId;
        this.onSale = onSale;
        this.restaurantName = restaurantName;
    }

    public MealModel(MealModel obj) {
        this.id = obj.getId();
        this.price = obj.getPrice();
        this.name = obj.getName();
        this.restaurantId = obj.getRestaurantId();
        this.onSale = obj.getOnSale();
        this.restaurantName = obj.getRestaurantName();
    }

    public static class MealModelComparator implements Comparator<MealModel> {

        @Override
        public int compare(MealModel o1, MealModel o2) {
            float delta = 0.01f;
            if (Math.abs(o1.getPrice() - o2.getPrice()) < delta) {
                return 0;
            }
            if (o1.getPrice() > o2.getPrice()) {
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

    public String getName() {
        return name;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public Float getOnSale() {
        return onSale;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setOnSale(Float onSale) {
        this.onSale = onSale;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
