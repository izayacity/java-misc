package com.izayacity.playground.mealplan.model;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/27/20
 * mailto:      xiruiyang@hiretual.com
 * version:     1.0 since 1.0
 */
public class Nutrition {

    private int energy;
    private int protein;
    private int vitamin;

    public Nutrition() {
    }

    public Nutrition(int energy, int protein, int vitamin) {
        this.energy = energy;
        this.protein = protein;
        this.vitamin = vitamin;
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
}
