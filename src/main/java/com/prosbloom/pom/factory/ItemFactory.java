package com.prosbloom.pom.factory;

import com.google.gson.Gson;
import com.prosbloom.pom.exception.ModifierExistsException;
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
    // TODO  - prefix and suffix shouldn't be separate.  should refactor to just modifiers with prefix/suffix being a property
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


    private NBTTagCompound rollPrefix(int ilvl) {
        // roll the prefix mods
        NBTTagCompound tag = new NBTTagCompound();
        List <Modifier>viablePrefixes = modifiers.getPrefixes().stream().filter(i->ilvl>= i.getIlvl()).collect(Collectors.toList());
        Prefix prefix = (Prefix)viablePrefixes.get(new Random().nextInt(viablePrefixes.size()));
        NBTTagCompound prefixNbt = prefix.toNbt();
        // physMod
        prefixNbt.setFloat(PomTag.MOD_DAMAGEMOD, 1 + (prefix.getDamageModRange()[0] + new Random().nextFloat() * (prefix.getDamageModRange()[1] - prefix.getDamageModRange()[0])));
        // save nbt
        return prefixNbt;
    }

    private NBTTagCompound rollSuffix(int ilvl) {
        // roll the suffix mods
        NBTTagCompound tag = new NBTTagCompound();
        List <Modifier>viableSuffixes = modifiers.getSuffixes().stream().filter(i->ilvl>= i.getIlvl()).collect(Collectors.toList());
        Suffix suffix = (Suffix)viableSuffixes.get(new Random().nextInt(viableSuffixes.size()));
        NBTTagCompound suffixNbt = suffix.toNbt();
        // spdMod
        suffixNbt.setFloat(PomTag.MOD_SPEEDMOD, suffix.getSpeedModRange()[0] + new Random().nextFloat() * (suffix.getSpeedModRange()[1] - suffix.getSpeedModRange()[0]));
        // save nbt
        return suffixNbt;
    }

    public ItemStack addPrefix(ItemStack stack) throws ModifierExistsException {
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        // check if prefix exists
        if (nbt.hasKey(PomTag.PREFIX))
                throw new ModifierExistsException();
        else
            nbt.setTag(PomTag.PREFIX, rollPrefix(
                    nbt.hasKey(PomTag.ILVL) ? nbt.getInteger(PomTag.ILVL) : 1));
        return stack;
    }
    public ItemStack addSuffix(ItemStack stack) throws ModifierExistsException {
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        // check if suffix exists
        if (nbt.hasKey(PomTag.SUFFIX))
            throw new ModifierExistsException();
        else
            nbt.setTag(PomTag.SUFFIX, rollSuffix(
                    nbt.hasKey(PomTag.ILVL) ? nbt.getInteger(PomTag.ILVL) : 1));
        return stack;
    }


    public ItemStack testGenerate(int ilvl) {
        ItemStack stack = new ItemStack(ModItems.modSword);
        // build nbt for item
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(PomTag.ILVL, ilvl);
        stack.setTagCompound(tag);

        // roll the mods
        try {
            stack = addPrefix(stack);
            stack = addSuffix(stack);
        } catch (ModifierExistsException e) {
           System.out.println(e.toString());
        }

        return stack;
    }
}
