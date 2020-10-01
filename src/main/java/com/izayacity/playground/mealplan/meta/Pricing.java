package com.izayacity.playground.mealplan.meta;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        9/22/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
@Root
public class Pricing {

    @ElementList(name = "item", required = false, inline = true)
    private List<PricingItem> itemList = new ArrayList<>();

    public List<PricingItem> getItemList() {
        return itemList;
    }
}
