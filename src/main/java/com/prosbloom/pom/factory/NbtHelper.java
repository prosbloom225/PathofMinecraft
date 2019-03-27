package com.prosbloom.pom.factory;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierNotFoundException;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class NbtHelper {

    private static ItemStack addModifier(ItemStack stack, Modifier mod) {
        return stack;
    }
    public static ItemStack addPrefix(ItemStack stack, Prefix prefix){
        return stack;
    }
    public static ItemStack addSuffix(ItemStack stack, Suffix suffix){
        return stack;
    }

    public static ItemStack clearPrefixes(ItemStack stack) {
        return clearModifier(stack, PomTag.PREFIX);
    }
    public static ItemStack clearSuffixes(ItemStack stack) {
        return clearModifier(stack, PomTag.SUFFIX);
    }
    public static ItemStack clearModifier(ItemStack stack, String tag) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(tag))
            stack.getTagCompound().removeTag(tag);
        else
            System.out.println("Couldn't find modifier to remove: " + tag);
        return stack;
    }
    public static ItemStack clearModifier(ItemStack stack, Modifier mod) throws ModifierNotFoundException {
        // this is dirty since im still separating suffix and prefixes
        if (Pom.itemFactory.getPrefix(mod.getName()) != null)
            return clearModifier(stack, PomTag.PREFIX);
        else if (Pom.itemFactory.getSuffix(mod.getName()) != null)
            return clearModifier(stack, PomTag.SUFFIX);
        else
            throw new ModifierNotFoundException();
    }

    public static boolean isDummy(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.DUMMY))
            return true;
        return false;
    }

    public static int getIlvl(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.ILVL))
            return stack.getTagCompound().getInteger(PomTag.ILVL);
        // default all items to ilvl 1, means we have to handle less exceptions
        // TODO - this is unsafe.  proceed with caution
        return 1;

    }

    public static NBTTagCompound getPrefixNbt(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.PREFIX))
            return stack.getTagCompound().getCompoundTag(PomTag.PREFIX);
        else
            return null;
    }

    public static Prefix getPrefix(ItemStack stack) throws ModifierNotFoundException {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.PREFIX))
            return (Prefix)Pom.itemFactory.getPrefix(stack.getTagCompound().getCompoundTag(PomTag.PREFIX).getString(PomTag.MOD_NAME));
        else
            throw new ModifierNotFoundException();
    }
    public static Suffix getSuffix(ItemStack stack) throws ModifierNotFoundException {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.SUFFIX))
            return (Suffix)Pom.itemFactory.getSuffix(stack.getTagCompound().getCompoundTag(PomTag.SUFFIX).getString(PomTag.MOD_NAME));
        else
            throw new ModifierNotFoundException();
    }

    public static List<Modifier> getModifiers(ItemStack stack) {
        List<Modifier> modifiers = new ArrayList<>();

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.PREFIX)) {
            modifiers.add(Pom.itemFactory.getPrefix(stack.getTagCompound().getCompoundTag(PomTag.PREFIX).getString(PomTag.MOD_NAME)));
        }
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.SUFFIX)) {
            modifiers.add(Pom.itemFactory.getSuffix(stack.getTagCompound().getCompoundTag(PomTag.SUFFIX).getString(PomTag.MOD_NAME)));
        }

        return modifiers;
    }
}
