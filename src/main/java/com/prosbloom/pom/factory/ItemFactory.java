package com.prosbloom.pom.factory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.items.interfaces.IModifiable;
import com.prosbloom.pom.model.Modifier;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ItemFactory {
    private static Logger logger;
    private static Gson gson;
    private List<Modifier> prefixes;


    public ItemFactory() {
        gson = new Gson();
        Type listType = new TypeToken<List<Modifier>>() {}.getType();
        prefixes = gson.fromJson(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/prefixes.json"))), listType);
    }
    public IModifiable regenerate(int ilvl, IModifiable item){
        // Get the list of prefixes available for our ilvl
        List <Modifier>viablePrefixes = prefixes.stream().filter(i->ilvl >= i.getIlvl()).collect(Collectors.toList());
        // Grab a random one and apply it
        item.addPrefix(viablePrefixes.get(new Random().nextInt(viablePrefixes.size())));
        return item;
    }

    public static void main(String[] args) {
        ItemFactory itemFactory = new ItemFactory();
        ModSword item = (ModSword)itemFactory.regenerate(99, new ModSword());
        System.out.println(item.getUnlocalizedName());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());
        System.out.println("DMG: " + item.getAttackDamage());

        System.out.println("end");
    }
}
