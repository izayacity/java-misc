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
public class NameWeightItem implements Index, Name, Weight {

    @Attribute
    private int index;

    @Attribute
    private int weight;

    @Attribute
    private String name;

    public NameWeightItem() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
