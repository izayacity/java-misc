package com.izayacity.playground.mealplan.model;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/27/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 *
 * @author francis
 */
public class Nutrition {

    private int energy;
    private int protein;
    private int vitamin;
    private int amount;

    public Nutrition() {
    }

    public Nutrition(int energy, int protein, int vitamin, int amount) {
        this.energy = energy;
        this.protein = protein;
        this.vitamin = vitamin;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }
}
