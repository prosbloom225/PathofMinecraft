package com.prosbloom.pom.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Drops {
    public List<Drop> getDrops() {
        List<Drop> drops = new ArrayList<>();
        drops.addAll(Arrays.asList(currency));
        return drops;
    }

    public List<Drop> getCurrency() {
        return Arrays.asList(currency);
    }
    public List<Drop> getRates() {
        return Arrays.asList(rates);
    }

    private Drop[] rates;
    private Drop[] currency;


}
