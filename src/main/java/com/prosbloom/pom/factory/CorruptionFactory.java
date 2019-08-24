package com.prosbloom.pom.factory;

import com.google.gson.Gson;
import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.common.RandomCollection;
import com.prosbloom.pom.model.Corruption;
import net.minecraft.item.ItemStack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CorruptionFactory {
    private static Gson gson;
    private List<Corruption> corruptions;

    public void dumpRates() {
    }

    public Corruption generateWeightedCorruption(ItemStack stack) {
        // TODO - do this on load for all types and store in factory.  doing every calculation is incredibly inefficient
        // then again how often do you corrupt....
        LibMisc.Types type = NbtHelper.getType(stack);
        List<Corruption> possibleCorruptions = corruptions.stream().filter(c->c.getWeightForType(NbtHelper.getType(stack)) > 0).collect(Collectors.toList());
        RandomCollection<Corruption> corruptionMap = new RandomCollection();
        possibleCorruptions.forEach(c->corruptionMap.add(c.getWeightForType(type), c));
        return corruptionMap.next();
    }

    public CorruptionFactory() {
        gson = new Gson();
        corruptions = Arrays.asList(gson.fromJson(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/corruptions.json"))), Corruption[].class));
        corruptions.forEach(c->
                System.out.println(c.toString()));


    }
}

