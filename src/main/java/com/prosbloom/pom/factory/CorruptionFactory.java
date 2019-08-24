package com.prosbloom.pom.factory;

import com.google.gson.Gson;
import com.prosbloom.pom.model.Corruption;
import net.minecraft.item.ItemStack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static com.prosbloom.pom.LibMisc.Types.SWORD;

public class CorruptionFactory {
    private static Gson gson;
    private List<Corruption> corruptions;

    public void dumpRates() {
    }

    public Corruption generateWeightedCorruption(ItemStack stack) {
        switch (NbtHelper.getType(stack)){
            case SWORD:
                System.out.println("Generating corruption for type: " + SWORD);

                break;
        }
        return null;
    }

    public CorruptionFactory() {
        gson = new Gson();
        corruptions = Arrays.asList(gson.fromJson(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/corruptions.json"))), Corruption[].class));
        corruptions.forEach(c->
                System.out.println(c.toString()));


    }
}

