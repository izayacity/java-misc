package com.izayacity.playground.mealplan.domain;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/27/20
 * mailto:      xiruiyang@hiretual.com
 * version:     1.0 since 1.0
 */
public class MealPlanDomain {

    public enum NutritionIndex {
        /**
         * low
         */
        LOW(0),
        /**
         * medium
         */
        MEDIUM(1),
        /**
         * high
         */
        HIGH(2);

        private final int value;

        NutritionIndex(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
