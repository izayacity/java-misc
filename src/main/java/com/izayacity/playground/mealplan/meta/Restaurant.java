package com.izayacity.playground.mealplan.meta;

import com.izayacity.playground.mealplan.meta.common.Index;
import com.izayacity.playground.mealplan.meta.common.Name;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
@Root
public class Restaurant implements Index, Name {

    @Attribute
    private int index;

    @Attribute(required = false)
    private String name;

    @Attribute
    private float packaging;

    @Element(required = false)
    private MealList meals;

    @Element(required = false)
    private Pricing pricing;

    @Attribute(required = false)
    private int disabled;

    public Restaurant() {
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    public float getPackaging() {
        return packaging;
    }

    public MealList getMeals() {
        return meals;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public int getDisabled() {
        return disabled;
    }
}
