package com.izayacity.playground.mealplan.meta;

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
@Root(name = "item")
public class NameWeightItemList {

    @Attribute(required = false)
    private int index;

    @Attribute(required = false)
    private String name;

    @ElementList(name = "item", inline = true, required = false)
    private List<NameWeightItem> itemList;

    public NameWeightItemList() {
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public List<NameWeightItem> getItemList() {
        return itemList;
    }
}
