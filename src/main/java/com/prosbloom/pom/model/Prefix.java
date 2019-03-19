package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTTagCompound;

public class Prefix extends Modifier {

    public float[] getDamageModRange() {
        return damageModRange;
    }

    private float[] damageModRange;
    // this should be set by the item factory
    private float damageMod = 0;


    public NBTTagCompound toNbt() {
        NBTTagCompound nbt = super.toNbt();
        nbt.setFloat(PomTag.MOD_DAMAGEMOD, damageMod);
        nbt.setFloat(PomTag.MOD_DAMAGERANGE_MIN, damageModRange[0]);
        nbt.setFloat(PomTag.MOD_DAMAGERANGE_MAX, damageModRange[1]);
        return nbt;
    }
}
