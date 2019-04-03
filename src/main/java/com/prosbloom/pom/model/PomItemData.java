package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public class PomItemData implements INBTSerializable {
    private String rarity;
    private int ilvl;
    private List<Prefix> prefixes;
    private List<Suffix> suffixes;


    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger(PomTag.ILVL, ilvl);
        nbt.setString(PomTag.RARITY, rarity);
        for (int i = 0; i < PomTag.PREFIXES.length; i++)
            nbt.setTag(PomTag.PREFIXES[i], prefixes.get(i).toNbt());
        for (int i = 0; i < PomTag.SUFFIXES.length; i++)
            nbt.setTag(PomTag.SUFFIXES[i], suffixes.get(i).toNbt());
        return null;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        // TODO - implement
    }
}
