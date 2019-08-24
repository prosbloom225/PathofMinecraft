package com.prosbloom.pom.model;

import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.ModItems;
import org.apache.logging.log4j.Logger;

public class Drop {
    public static Logger logger;

    public double getDropRate() {
        return rate;
    }
    public BaseItem getItem() {

        BaseItem itm = ModItems.getItems().stream()
                .filter(i-> name.contains(i.getClass().getCanonicalName()))
                .findFirst().orElse(null);
        return itm;
    }

    private double rate;

    public String getName() {
        return name;
    }

    private String name;

    @Override
    public String toString() {
        return String.format("%s: %.5f", this.name, this.rate);
    }
}
