package com.prosbloom.pom.factory;

import com.google.gson.Gson;
import com.prosbloom.pom.common.RandomCollection;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.model.Drop;
import com.prosbloom.pom.model.Drops;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DropFactory {
    private static Gson gson;
    public static RandomCollection<BaseItem> currencyTable;

    public BaseItem generateWeightedCurrency() {
        return currencyTable.next();
    }
    public DropFactory() {
        gson = new Gson();
        Drops drops = gson.fromJson(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/drops.json"))), Drops.class);
        currencyTable = new RandomCollection<>();
        drops.getCurrency().forEach(d-> currencyTable.add(d.getDropRate(), d.getItem()));
    }
}

