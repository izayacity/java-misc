package com.izayacity.playground.mealplan.meta;

import com.izayacity.playground.mealplan.meta.common.Index;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        9/22/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 *
 * @author francis
 */
@Root(name = "item")
public class PricingItem implements Index {

    @Attribute
    private int index;

    @Attribute
    private float condition;

    @Attribute
    private float discount;

    @Override
    public int getIndex() {
        return index;
    }

    public float getCondition() {
        return condition;
    }

    public float getDiscount() {
        return discount;
    }
}
