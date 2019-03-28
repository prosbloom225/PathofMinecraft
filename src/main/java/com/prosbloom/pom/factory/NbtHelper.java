package com.prosbloom.pom.factory;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.exception.ModifierExistsException;
import com.prosbloom.pom.exception.ModifierNotFoundException;
import com.prosbloom.pom.exception.NoModifierSlotsAvailableException;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NbtHelper {

    public static void addPrefix(ItemStack stack, Prefix prefix) throws ModifierException{
        checkOrCreateNbt(stack);
        if (stack.getTagCompound().getKeySet().stream().anyMatch(PomTag.PREFIXES::equals))
            throw new ModifierException();
        // get first available prefix slot
        String prefixTag = "";
        for (int i=0; i <PomTag.PREFIXES.length; i++){
            if (!stack.getTagCompound().hasKey(PomTag.PREFIXES[i])) {
                prefixTag = PomTag.PREFIXES[i];
                break;
            }
        }
        if (prefixTag != "")
            stack.getTagCompound().setTag(prefixTag, prefix.toNbt());
        else
            throw new ModifierException();

    }
    public static void addSuffix(ItemStack stack, Suffix suffix) throws ModifierException{
        checkOrCreateNbt(stack);
        if (stack.getTagCompound().getKeySet().stream().anyMatch(PomTag.SUFFIXES::equals))
            throw new ModifierException();
        // get first available suffix slot
        String suffixTag = "";
        for (int i=0; i <PomTag.SUFFIXES.length; i++){
            if (!stack.getTagCompound().hasKey(PomTag.SUFFIXES[i])) {
                suffixTag = PomTag.SUFFIXES[i];
                break;
            }
        }
        if (suffixTag != "")
            stack.getTagCompound().setTag(suffixTag, suffix.toNbt());
        else
            throw new ModifierException();
    }

    public static ItemStack clearPrefixes(ItemStack stack) {
        return clearModifier(stack, PomTag.PREFIXES);
    }
    public static ItemStack clearSuffixes(ItemStack stack) {
        return clearModifier(stack, PomTag.SUFFIXES);
    }

    public static ItemStack clearModifier(ItemStack stack, String []tags) {
        Arrays.stream(tags).forEach(tag->clearModifier(stack, tag));
        return stack;
    }
    public static ItemStack clearModifier(ItemStack stack, String tag) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(tag))
            stack.getTagCompound().removeTag(tag);
        else
            System.out.println("Couldn't find modifier to remove: " + tag);
        return stack;
    }
    public static ItemStack clearModifier(ItemStack stack, Modifier mod) throws ModifierException{
        // this is dirty since im still separating suffix and prefixes
        String tag;
        if (Pom.itemFactory.getPrefix(mod.getName()) != null) {
            for (int i = 0; i < PomTag.PREFIXES.length; i++) {
                Prefix prefix = new Prefix(stack.getTagCompound().getCompoundTag(PomTag.PREFIXES[i]));
                if (prefix.getName().equals(mod.getName()))
                    return clearModifier(stack, PomTag.PREFIXES[i]);
            }
            throw new ModifierException();
        } else if (Pom.itemFactory.getSuffix(mod.getName()) != null)
            for (int i = 0; i < PomTag.SUFFIXES.length; i++) {
                Suffix suffix = new Suffix(stack.getTagCompound().getCompoundTag(PomTag.SUFFIXES[i]));
                if (suffix.getName().equals(mod.getName()))
                    return clearModifier(stack, PomTag.SUFFIXES[i]);
            }
        else
            throw new ModifierNotFoundException();
        return null;
    }
    public static ItemStack clearModifiers(ItemStack stack) throws ModifierException{
        List<Modifier> mods = NbtHelper.getModifiers(stack);
        if (mods.size() > 0) {
            for (Modifier mod : mods) {
                NbtHelper.clearModifier(stack, mod);
            }
            return stack;
        } else {
            throw new ModifierNotFoundException();
        }
    }

    private static boolean checkOrCreateNbt(ItemStack stack){
        // any nbt initialization can happen here
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        return true;
    }

    public static boolean isDummy(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.DUMMY))
            return true;
        return false;
    }
    public static void setDummy(ItemStack stack, boolean dummy){
        checkOrCreateNbt(stack);
        stack.getTagCompound().setString(PomTag.DUMMY, "dummy");
    }

    public static int getIlvl(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.ILVL))
            return stack.getTagCompound().getInteger(PomTag.ILVL);
        // default all items to ilvl 1, means we have to handle less exceptions
        // TODO - this is unsafe.  proceed with caution
        return 1;

    }
    public static void setIlvl(ItemStack stack, int ilvl) {
        checkOrCreateNbt(stack);
        stack.getTagCompound().setInteger(PomTag.ILVL, ilvl);
    }


    public static List<Prefix> getPrefixes(ItemStack stack) throws ModifierNotFoundException{
        if (hasPrefix(stack))
            return getModifiers(stack).stream()
                    .filter(m->m instanceof Prefix)
                    .map(m->(Prefix)m)
                    .collect(Collectors.toList());
        else
            throw new ModifierNotFoundException();
    }
    public static List<Suffix> getSuffixes(ItemStack stack) throws ModifierNotFoundException{
        if (hasSuffix(stack))
            return getModifiers(stack).stream()
                    .filter(m->m instanceof Suffix)
                    .map(m->(Suffix)m)
                    .collect(Collectors.toList());
        else
            throw new ModifierNotFoundException();
    }
    public static boolean hasPrefix(ItemStack stack) {
        // TODO - could probably optimize and just look for a tag
        return getModifiers(stack).stream()
                .filter(mod->mod instanceof Prefix)
                .collect(Collectors.toList()).size() > 0;
    }
    public static boolean hasSuffix(ItemStack stack) {
        // TODO - could probably optimize and just look for a tag
        return getModifiers(stack).stream()
                .filter(mod->mod instanceof Suffix)
                .collect(Collectors.toList()).size() > 0;
    }

    public static List<Modifier> getModifiers(ItemStack stack) {
        List<Modifier> modifiers = new ArrayList<>();

        for (String p : PomTag.PREFIXES) {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey(p)) {
                modifiers.add(new Prefix(stack.getTagCompound().getCompoundTag(p)));
            }
        }
        for (String s : PomTag.SUFFIXES) {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey(s)) {
                modifiers.add(new Suffix(stack.getTagCompound().getCompoundTag(s)));
            }
        }
        return modifiers;
    }
}
