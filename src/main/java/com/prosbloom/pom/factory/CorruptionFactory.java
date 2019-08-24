package com.prosbloom.pom.factory;

import com.google.gson.Gson;
import com.prosbloom.pom.common.RandomCollection;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.currency.AlchemyOrb;
import com.prosbloom.pom.model.Drop;
import com.prosbloom.pom.model.Drops;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class DropFactory {
    private static Gson gson;
    private static RandomCollection<BaseItem> currencyTable;
    private static Map<Double, String> dropRates;
    private double dropWeightTotal;

    public void dumpRates() {
    }

    public BaseItem generateWeightedCurrency() {
        BaseItem item = currencyTable.next();
        if (item == null)
            return new BaseItem();
        return item;
    }

    public List<ItemStack> generateWeightedDrops(int count, int ilvl) {
        Random rng = new Random();
        List<ItemStack> drops = new ArrayList<>();
        for (int i = 0; i < count; i++){
            double curr = dropWeightTotal  * rng.nextDouble();
            // TODO - can probably use an enum here for the various types.
            // keeping as is cuz i only have currency types for now.
            for (Map.Entry<Double, String> rate : dropRates.entrySet()) {
                if (curr <= rate.getKey())
                    switch (rate.getValue()){
                        case "Currency":
                            drops.add(new ItemStack(generateWeightedCurrency(), 1));
                            break;
                    }
                break;
            }
        }

        return drops;
    }
    public DropFactory() {
        gson = new Gson();
        Drops drops = gson.fromJson(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/drops.json"))), Drops.class);

        // drop types
        currencyTable = new RandomCollection<>();
        drops.getCurrency().forEach(d-> currencyTable.add(d.getDropRate(), d.getItem()));

        dropRates = new TreeMap<>();
        double curr = 0;
        for (Drop rate : drops.getRates()) {
            dropRates.put((curr +=rate.getDropRate()), rate.getName());
            dropWeightTotal += rate.getDropRate();
        }

    }
}

