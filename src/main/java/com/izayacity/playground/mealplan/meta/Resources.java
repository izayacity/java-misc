package com.izayacity.playground.mealplan.meta;

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
public class Resources {

    @ElementList(name = "app", inline = true)
    private List<App> appList;

    public Resources() {
    }

    public List<App> getAppList() {
        return appList;
    }
}
