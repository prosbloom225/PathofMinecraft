package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTTagCompound;

public class Modifier {
    private String name;
    private int ilvl;
    private int tier;
    private float[] damageModRange;
    // this should be set by the item factory
    private float damageMod = 0;

    public int getTier() {
        return tier;
    }
    public void setTier(int tier) {
        this.tier = tier;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIlvl() {
        return ilvl;
    }
    public void setIlvl(int ilvl) {
        this.ilvl = ilvl;
    }
    public float[] getDamageModRange() {
        return damageModRange;
    }
    public void setDamageModRange(float[] damageModRange) {
        this.damageModRange = damageModRange;
    }

    @Override
    public String toString() {
        return String.format("T%s-%s(%s)", tier,name, ilvl);
    }

    public NBTTagCompound toNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(PomTag.MOD_NAME, this.getName());
        nbt.setInteger(PomTag.MOD_ILVL, this.getIlvl());
        nbt.setInteger(PomTag.MOD_TIER, this.getTier());
        nbt.setFloat(PomTag.MOD_DAMAGERANGE_MIN, this.getDamageModRange()[0]);
        nbt.setFloat(PomTag.MOD_DAMAGERANGE_MAX, this.getDamageModRange()[1]);
        nbt.setFloat(PomTag.MOD_DAMAGEMOD, damageMod);
        return nbt;
    }
}
