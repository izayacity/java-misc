package com.izayacity.playground.mealplan.meta;

import com.izayacity.playground.mealplan.meta.common.Index;
import com.izayacity.playground.mealplan.meta.common.Name;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
@Root
public class App implements Index, Name {

    @Attribute
    private int index;

    @Attribute
    private String name;

    @ElementList(name = "restaurant", required = false, inline = true)
    private List<Restaurant> restaurantList;

    public App() {
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}
