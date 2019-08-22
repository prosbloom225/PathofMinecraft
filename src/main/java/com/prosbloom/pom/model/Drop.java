package com.prosbloom.pom.model;

import com.prosbloom.pom.items.BaseItem;
import org.apache.logging.log4j.Logger;

public class Drop {
    public static Logger logger;

    public double getDropRate() {
        return rate;
    }
    public BaseItem getItem() {
        try {
            return (BaseItem) Class.forName(name).newInstance();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new BaseItem();
    }

    private double rate;
    private String name;

    @Override
    public String toString() {
        return String.format("%s: %.5f", this.name, this.rate);
    }
}
