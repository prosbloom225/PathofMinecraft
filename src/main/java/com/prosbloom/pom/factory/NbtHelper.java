package com.prosbloom.pom.factory;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.model.*;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NbtHelper {
    // TODO - get these dynamically
    protected static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    public static void addModifiers(ItemStack stack, List<Modifier> mods) throws ModifierException{
        for (Modifier mod : mods)
            addModifier(stack, mod);
    }
    public static void addModifier(ItemStack stack, Modifier mod) throws ModifierException {
        PomItemData item = getNbt(stack);
        if (mod instanceof Prefix && item.getPrefixes().size() < PomTag.PREFIXES.length) {
            item.addModifier(mod);
        } else if (mod instanceof Suffix && item.getSuffixes().size() < PomTag.SUFFIXES.length) {
            item.addModifier(mod);
        }else
            throw new ModifierException();
        writeNbt(stack, item);
    }

    public static void clearPrefixes(ItemStack stack) {
        try {
            for (Prefix prefix : getPrefixes(stack)) {
                NbtHelper.clearModifier(stack, prefix);
            }
        } catch (ModifierException e) {
            System.out.println("No modifiers to clear..");
        }
    }
    public static void clearSuffixes(ItemStack stack) {
        try {
            for (Suffix suffix : getSuffixes(stack)) {
                NbtHelper.clearModifier(stack, suffix);
            }
        } catch (ModifierException e) {
            System.out.println("No modifiers to clear..");
        }
    }

    public static void clearModifier(ItemStack stack, Modifier mod) throws ModifierException{
        PomItemData item = getNbt(stack);
        for (Modifier modifier : item.getModifiers())
            if (mod.equals(modifier)) {
                item.getModifiers().remove(modifier);
                break;
            }
        // reserialize
        writeNbt(stack, item);
    }
    public static void clearModifiers(ItemStack stack) {
        PomItemData item = getNbt(stack);
        item.setModifiers(new ArrayList<Modifier>());
        writeNbt(stack, item);
    }

    // item level nbt
    public static PomItemData getNbt(ItemStack stack){
        // any nbt initialization can happen here
        PomItemData item = new PomItemData();
        // create the nbt and save if it doesnt exist
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.POM_TAG)) {
            item = new PomItemData(stack.getTagCompound().getCompoundTag(PomTag.POM_TAG));
        } else {
            NbtHelper.writeNbt(stack, item);
        }
        return item;
    }
    public static void writeNbt(ItemStack stack, PomItemData item) {
        NBTTagCompound nbt,pomNbt;
        if (stack.hasTagCompound())
            nbt = stack.getTagCompound();
        else
            nbt = new NBTTagCompound();
        pomNbt = item.serializeNbt();
        nbt.setTag(PomTag.POM_TAG, pomNbt);
        stack.setTagCompound(nbt);
    }
    public static boolean hasTag(ItemStack stack) {
        return stack.hasTagCompound()
                && stack.getTagCompound().hasKey(PomTag.POM_TAG);
    }

    public static boolean isDummy(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.DUMMY))
            return true;
        return false;
    }
    public static void setDummy(ItemStack stack, boolean dummy){
        getNbt(stack);
        stack.getTagCompound().setString(PomTag.DUMMY, "dummy");
    }
    public static LibMisc.Rarity getRarity(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getRarity();
    }
    public static void setRarity(ItemStack stack, LibMisc.Rarity rarity) {
        PomItemData item = getNbt(stack);
        item.setRarity(rarity);
        writeNbt(stack, item);
    }
    public static LibMisc.Types getType(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getType();
    }
    public static void setType(ItemStack stack, LibMisc.Types type) {
        PomItemData item = getNbt(stack);
        item.setType(type);
        writeNbt(stack, item);
    }
    public static Corruption getCorruption(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getCorruption();
    }
    public static void setCorruption(ItemStack stack, Corruption corruption) {
        PomItemData item = getNbt(stack);
        item.setCorruption(corruption);
        writeNbt(stack, item);
    }


    public static int getIlvl(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.ilvl;
        /*
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.ILVL))
            return stack.getTagCompound().getInteger(PomTag.ILVL);
        // default all items to ilvl 1, means we have to handle less exceptions
        // TODO - this is unsafe.  proceed with caution
        return 1;
        */

    }
    public static void setIlvl(ItemStack stack, int ilvl) {
        PomItemData item = getNbt(stack);
        item.ilvl = ilvl;
        writeNbt(stack, item);
    }
    public static boolean getMirrored(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.isMirrored();
    }
    public static void setMirrored(ItemStack stack, boolean mirror) {
        PomItemData item = getNbt(stack);
        item.setMirrored(mirror);
        writeNbt(stack, item);
    }

    public static boolean getCorrupted(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.isCorrupted();
    }
    public static void setCorrupted(ItemStack stack, boolean corrupt) {
        PomItemData item = getNbt(stack);
        item.setCorrupted(corrupt);
        writeNbt(stack, item);
    }

    public static List<Prefix> getPrefixes(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getPrefixes();
    }
    public static List<Suffix> getSuffixes(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getSuffixes();
    }

    public static List<Modifier> getModifiers(ItemStack stack) {
        return getNbt(stack).getModifiers();
    }
}
