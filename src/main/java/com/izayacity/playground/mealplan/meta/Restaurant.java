package com.izayacity.playground.mealplan.meta;

import com.izayacity.playground.mealplan.meta.common.Index;
import com.izayacity.playground.mealplan.meta.common.Name;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

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
    private float delivery;

    @ElementList(name = "item", required = false, inline = true)
    private List<Meal> mealList = new ArrayList<>();

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

    public float getDelivery() {
        return delivery;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public int getDisabled() {
        return disabled;
    }
}
