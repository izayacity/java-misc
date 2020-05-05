package com.izayacity.playground.mealplan.meta;

import com.izayacity.playground.mealplan.meta.common.Index;
import com.izayacity.playground.mealplan.meta.common.Name;
import com.izayacity.playground.mealplan.meta.common.Weight;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
@Root(name = "item")
public class Meal implements Index, Name, Weight {

    @Attribute
    private int index;

    @Attribute
    private String id;

    @Attribute
    private int weight;

    @Attribute
    private float price;

    @Attribute
    private int staple;

    @Attribute
    private int category;

    @Attribute
    private int energy;

    @Attribute
    private int protein;

    @Attribute
    private int vitamin;

    @Attribute
    private String name;

    @Attribute(required = false)
    private int disabled;

    @Attribute
    private int amount;

    public Meal() {
    }

    public Meal(Meal obj) {
        this.index = obj.getIndex();
        this.id = obj.getId();
        this.weight = obj.getWeight();
        this.price = obj.getPrice();
        this.staple = obj.getStaple();
        this.category = obj.getCategory();
        this.energy = obj.getEnergy();
        this.protein = obj.getProtein();
        this.vitamin = obj.getVitamin();
        this.name = obj.getName();
        this.disabled = obj.getDisabled();
        this.amount = obj.getAmount();
    }

    @Override
    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    public float getPrice() {
        return price;
    }

    public int getStaple() {
        return staple;
    }

    public int getCategory() {
        return category;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProtein() {
        return protein;
    }

    public int getVitamin() {
        return vitamin;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getDisabled() {
        return disabled;
    }

    public int getAmount() {
        return amount;
    }
}
