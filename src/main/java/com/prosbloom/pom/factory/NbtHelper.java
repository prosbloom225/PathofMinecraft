package com.prosbloom.pom.factory;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.item.ItemStack;

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


    public List<Modifier> getModifiers(ItemStack stack) {
        List<Modifier> modifiers = new ArrayList<>();

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.PREFIX)) {
            modifiers.add(Pom.itemFactory.getPrefix(stack.getTagCompound().getCompoundTag(PomTag.PREFIX).getString(PomTag.MOD_NAME)));
        }
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.SUFFIX)) {
            modifiers.add(Pom.itemFactory.getPrefix(stack.getTagCompound().getCompoundTag(PomTag.SUFFIX).getString(PomTag.MOD_NAME)));
        }

        return modifiers;
    }
}
