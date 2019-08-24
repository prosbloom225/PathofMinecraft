package com.prosbloom.pom.factory;

import com.google.gson.Gson;
import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.items.currency.ChaosOrb;
import com.prosbloom.pom.items.currency.TransmutationOrb;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.Modifiers;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.apache.logging.log4j.Logger;
import static com.prosbloom.pom.LibMisc.Types.*;
import static com.prosbloom.pom.LibMisc.Rarity.*;

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

    public ItemFactory() {
        gson = new Gson();
        //Type listType = new TypeToken<List<Modifiers>>() {}.getType();
        modifiers = gson.fromJson(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/1hSword.json"))), Modifiers.class);
    }

    // TODO  - prefix and suffix shouldn't be separate.  should refactor to just modifiers with prefix/suffix being a property
    public Modifier getPrefix(String name) {
        return modifiers.getPrefixes().stream()
                .filter(s -> name.equals(s.getName()))
                .findFirst()
                .orElse(null);
    }

    public Modifier getSuffix(String name) {
        return modifiers.getSuffixes().stream()
                .filter(s -> name.equals(s.getName()))
                .findFirst()
                .orElse(null);
    }

    // rolls
    public Prefix rollPrefix(int ilvl) {
        // grab a viable prefix
        List<Modifier> viablePrefixes = modifiers.getPrefixes().stream().filter(i -> ilvl >= i.getIlvl()).collect(Collectors.toList());
        Prefix prefix = (Prefix) viablePrefixes.get(new Random().nextInt(viablePrefixes.size()));
        return rollPrefix(prefix);
    }

    public Prefix rollPrefix(Prefix prefix) {
        // roll the prefix mods
        prefix.setDamageMod(1 + prefix.getDamageModRange()[0] + new Random().nextFloat() * (prefix.getDamageModRange()[1] - prefix.getDamageModRange()[0]));
        return prefix;

    }

    public void rerollPrefix(ItemStack stack) {
        try {
            List<Prefix> prefix = NbtHelper.getPrefixes(stack);
            NbtHelper.clearPrefixes(stack);
            for (Prefix p : prefix)
                NbtHelper.addModifier(stack, rollPrefix(p));
        } catch (ModifierException e) {
            System.out.println("Somehow the clear modifiers failed: " + e.toString());
        }
    }

    public Suffix rollSuffix(Suffix suffix) {
        // roll the suffix mods
        suffix.setSpeedMod(suffix.getSpeedModRange()[0] + new Random().nextFloat() * (suffix.getSpeedModRange()[1] - suffix.getSpeedModRange()[0]));
        return suffix;
    }

    public Suffix rollSuffix(int ilvl) {
        // grab a viable suffix
        List<Modifier> viableSuffixes = modifiers.getSuffixes().stream().filter(i -> ilvl >= i.getIlvl()).collect(Collectors.toList());
        Suffix suffix = (Suffix) viableSuffixes.get(new Random().nextInt(viableSuffixes.size()));
        return rollSuffix(suffix);
    }

    public void rerollSuffix(ItemStack stack) {
        try {
            List<Suffix> suffix = NbtHelper.getSuffixes(stack);
            NbtHelper.clearSuffixes(stack);
            for (Suffix s : suffix)
                NbtHelper.addModifier(stack, rollSuffix(s));
        } catch (ModifierException e) {
            System.out.println("Somehow the clear modifiers failed: " + e.toString());
        }
    }


    public ItemStack testGenerate(int ilvl) {
        ItemStack stack = new ItemStack(ModItems.modSword);
        NbtHelper.setIlvl(stack, ilvl); //this generates the base nbt
        NbtHelper.setRarity(stack, LibMisc.Rarity.getRandomRarity());

        // roll the mods
        try {
            NbtHelper.addModifier(stack, rollPrefix(NbtHelper.getIlvl(stack)));
            NbtHelper.addModifier(stack, rollPrefix(NbtHelper.getIlvl(stack)));
            NbtHelper.addModifier(stack, rollSuffix(NbtHelper.getIlvl(stack)));
            NbtHelper.addModifier(stack, rollSuffix(NbtHelper.getIlvl(stack)));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return stack;
    }

    public void generateItem(ItemStack stack, int ilvl, LibMisc.Types type) {
        NbtHelper.setIlvl(stack, ilvl);
        NbtHelper.setType(stack, type);
        //NbtHelper.setRarity(stack, getRandomRarity());
        // TODO - temp shim
        NbtHelper.setRarity(stack, RARE);
            // process rarity and add modifiers
            // TODO - just gonna use currency for the time.  this may be right or wrong
            switch (NbtHelper.getRarity(stack)) {
                case NORMAL:
                    break;
                case MAGIC:
                    new TransmutationOrb().process(stack);
                    break;
                case RARE:
                    new ChaosOrb().process(stack);
                    break;
                case UNIQUE:
                    // TODO - should probably build some stub uniques and implement functionality
                    // will need to determine how to handle random uniqyes
                    break;
        }
        NbtHelper.processItemData(stack);
    }
}
