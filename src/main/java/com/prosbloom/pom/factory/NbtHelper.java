package com.prosbloom.pom.factory;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NbtHelper {

    private static void addPrefix(ItemStack stack, Prefix prefix) throws ModifierException{
        PomItemData item = getNbt(stack);
        if (item.getPrefixes().size() < PomTag.PREFIXES.length) {
            item.addModifier(prefix);
            writeNbt(stack, item);
        } else
            throw new ModifierException();
    }
    private static void addSuffix(ItemStack stack, Suffix suffix) throws ModifierException{
        PomItemData item = getNbt(stack);
        if (item.getSuffixes().size() < PomTag.SUFFIXES.length) {
            item.addModifier(suffix);
            writeNbt(stack, item);
        } else
            throw new ModifierException();
    }
    public static void addModifiers(ItemStack stack, List<Modifier> mods) throws ModifierException{
        for (Modifier mod : mods)
            addModifier(stack, mod);
    }
    public static void addModifier(ItemStack stack, Modifier mod) throws ModifierException {
        if (mod instanceof Prefix)
            addPrefix(stack, (Prefix) mod);
        else if (mod instanceof Suffix)
            addSuffix(stack, (Suffix) mod);
        else
            throw new ModifierException();
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
        List<Modifier> mods = NbtHelper.getModifiers(stack);
        for (Modifier modifier : mods)
            if (mod.equals(modifier)) {
                mods.remove(modifier);
                break;
            }
        NbtHelper.clearModifiers(stack);
        // reserialize
        NbtHelper.addModifiers(stack, mods);
    }
    public static void clearModifiers(ItemStack stack) {
        // this is cheaty, but more efficient
        PomItemData item = getNbt(stack);
        item.setModifiers(new ArrayList<Modifier>());
        writeNbt(stack, item);
    }

    // item level nbt
    public static PomItemData getNbt(ItemStack stack){
        // any nbt initialization can happen here
        PomItemData item = new PomItemData();
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.POM_TAG)) {
            item = new PomItemData(stack.getTagCompound().getCompoundTag(PomTag.POM_TAG));
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
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.RARITY))
                return LibMisc.Rarity.valueOf(stack.getTagCompound().getString(PomTag.RARITY));
        else return LibMisc.Rarity.NORMAL;
    }
    public static void setRarity(ItemStack stack, LibMisc.Rarity rarity) {
        getNbt(stack);
        stack.getTagCompound().setString(PomTag.RARITY, rarity.toString());
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


    public static List<Prefix> getPrefixes(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getPrefixes();
    }
    public static List<Suffix> getSuffixes(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getSuffixes();
    }

    public static List<Modifier> getModifiers(ItemStack stack) {
        List<Modifier> modifiers = new ArrayList<>();

        for (String p : PomTag.PREFIXES) {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey(p)) {
                modifiers.add(new Prefix(stack.getTagCompound().getCompoundTag(p)));
            }
        }
        PomItemData item = getNbt(stack);
        for (Prefix p : item.getPrefixes())
            modifiers.add(p);
        for (String s : PomTag.SUFFIXES) {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey(s)) {
                modifiers.add(new Suffix(stack.getTagCompound().getCompoundTag(s)));
            }
        }
        return modifiers;
    }
}
