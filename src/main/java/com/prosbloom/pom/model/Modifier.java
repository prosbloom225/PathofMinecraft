package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTTagCompound;

public class Modifier {
    public String getName() {
        return name;
    }

    private String name;

    public int getIlvl() {
        return ilvl;
    }

    private int ilvl;

    public int getTier() {
        return tier;
    }

    private int tier;
    @Override
    public String toString() {
        return String.format("T%s-%s(%s)", tier,name, ilvl);
    }

    public NBTTagCompound toNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(PomTag.MOD_NAME, name);
        nbt.setInteger(PomTag.MOD_ILVL, ilvl);
        nbt.setInteger(PomTag.MOD_TIER, tier);
        return nbt;
    }
}
