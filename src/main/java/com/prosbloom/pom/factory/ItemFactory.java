package com.prosbloom.pom.factory;

import com.google.gson.Gson;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.model.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ItemFactory {
    // TODO - should these factories be static?  set the constructor stuff in a static block and just reference statically???
    private static Logger logger;
    private static Gson gson;
    private Modifiers modifiers;
    //private List<Modifier> prefixes;


    public ItemFactory() {
        gson = new Gson();
        //Type listType = new TypeToken<List<Modifiers>>() {}.getType();
        modifiers = gson.fromJson(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/prefixes.json"))), Modifiers.class);
    }

    public Modifier getPrefix (String name) {
        return modifiers.getPrefixes().stream()
                .filter(s-> name.equals(s.getName()))
                .findFirst()
                .orElse(null);
    }
    public Modifier getSuffix (String name) {
        return modifiers.getSuffixes().stream()
                .filter(s-> name.equals(s.getName()))
                .findFirst()
                .orElse(null);
    }

    public ItemStack testGenerate(int ilvl) {
        ItemStack itemStack = new ItemStack(ModItems.modSword);
        List <Modifier>viablePrefixes = modifiers.getPrefixes().stream().filter(i->ilvl>= i.getIlvl()).collect(Collectors.toList());
        List <Modifier>viableSuffixes = modifiers.getSuffixes().stream().filter(i->ilvl>= i.getIlvl()).collect(Collectors.toList());
        Prefix prefix = (Prefix)viablePrefixes.get(new Random().nextInt(viablePrefixes.size()));
        Suffix suffix = (Suffix)viableSuffixes.get(new Random().nextInt(viableSuffixes.size()));

        // build nbt for item
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(PomTag.ILVL, ilvl);

        // roll the prefix mods
        NBTTagCompound prefixNbt = prefix.toNbt();
        float physMod =1 + (prefix.getDamageModRange()[0] + new Random().nextFloat() * (prefix.getDamageModRange()[1] - prefix.getDamageModRange()[0]));
        System.out.println("PHYSMOD: " + physMod);
        prefixNbt.setFloat(PomTag.MOD_DAMAGEMOD, physMod);
        tag.setTag(PomTag.PREFIX, prefixNbt);

        // roll the suffix mods
        NBTTagCompound suffixNbt = suffix.toNbt();
        float spdMod =suffix.getSpeedModRange()[0] + new Random().nextFloat() * (suffix.getSpeedModRange()[1] - suffix.getSpeedModRange()[0]);
        System.out.println("SPDMOD: " + physMod);
        suffixNbt.setFloat(PomTag.MOD_SPEEDMOD, spdMod);
        // create the prefix
        tag.setTag(PomTag.SUFFIX, suffixNbt);



        // save the item
        itemStack.setTagCompound(tag);
        return itemStack;
    }

    public static void main(String[] args) {
        ItemFactory itemFactory = new ItemFactory();
        ItemStack stack = itemFactory.testGenerate(1);
        ModSword ms = new ModSword();
        System.out.println("DMG: " + ms.getAttackDamage());

        System.out.println("end");
    }
}
