package com.izayacity.playground.mealplan.meta;

import org.simpleframework.xml.Element;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
public class FoodAttributes {

    @Element
    private NameWeightItemList stapleFood;

    @Element
    private NameWeightItemList category;

    @Element
    private NameWeightItemLists nutritions;

    public FoodAttributes() {
    }

    public NameWeightItemList getStapleFood() {
        return stapleFood;
    }

    public NameWeightItemList getCategory() {
        return category;
    }

    public NameWeightItemLists getNutritions() {
        return nutritions;
    }
}
