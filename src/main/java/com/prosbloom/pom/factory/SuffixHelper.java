package com.prosbloom.pom.factory;

import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class SuffixHelper {
    /*
     * This class handles actual rolling of suffixes.
     * It gets to touch nbt directly
     */
    public ItemStack generateSuffix(ItemStack stack, Suffix suffix) {
        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        NBTTagCompound tag = suffix.toNbt();
        tag.setFloat(PomTag.MOD_SPEEDMOD, suffix.getSpeedModRange()[0] + new Random().nextFloat() * (suffix.getSpeedModRange()[1] - suffix.getSpeedModRange()[0]));
        nbt.setTag(PomTag.SUFFIX, tag);
        return stack;
    }
}
