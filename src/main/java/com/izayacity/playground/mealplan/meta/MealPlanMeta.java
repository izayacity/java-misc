package com.izayacity.playground.mealplan;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        4/26/20
 * mailto:      xiruiyang@hiretual.com
 * version:     1.0 since 1.0
 */
@Root
public class MealPlanMeta {

    @Attribute
    private int version;

    @Element
    private String resources;
}
