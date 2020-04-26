package com.izayacity.playground.mealplan.meta;

import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
public class NameWeightItemLists {

    @ElementList(name = "item", inline = true, required = false)
    private List<NameWeightItemList> itemLists;

    public NameWeightItemLists() {
    }

    public List<NameWeightItemList> getItemLists() {
        return itemLists;
    }
}
