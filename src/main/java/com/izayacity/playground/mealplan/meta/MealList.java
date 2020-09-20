package com.izayacity.playground.mealplan.meta;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        9/20/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
@Root
public class MealList {

    @ElementList(name = "item", required = false, inline = true)
    private List<Meal> itemList = new ArrayList<>();

    public List<Meal> getItemList() {
        return itemList;
    }
}
