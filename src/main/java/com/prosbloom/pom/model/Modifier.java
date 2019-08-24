package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public class Modifier {

    public String name;
    public int ilvl;
    public int tier;

    public Modifier(String name, int ilvl, int tier) {
        this.name = name;
        this.ilvl = ilvl;
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

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return String.format("T%s-%s(%s)", tier,name, ilvl);
    }


    // deserialize nbt
    public Modifier(NBTTagCompound nbt) {
        this.setName(nbt.hasKey(PomTag.MOD_NAME) ? nbt.getString(PomTag.MOD_NAME) : "modifier");
        this.setIlvl(nbt.hasKey(PomTag.ILVL) ? nbt.getInteger(PomTag.ILVL) : 1);
        this.setTier(nbt.hasKey(PomTag.MOD_TIER) ? nbt.getInteger(PomTag.MOD_TIER) : 1);
    }

    public String getAdvTooltip(){
        return String.format(TextFormatting.GRAY + "Mod \"%s\" (%d)\n" + TextFormatting.RESET,
                this.getName(), this.getTier());
    }



    @Override
    public boolean equals(Object obj) {
        Modifier other = (Modifier)obj;
        return (this.getName().equals(other.getName())
                && this.getIlvl() == other.getIlvl()
                && this.getTier() == other.getTier()
        );
    }

    public NBTTagCompound serializeNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(PomTag.MOD_NAME, name);
        nbt.setInteger(PomTag.MOD_ILVL, ilvl);
        nbt.setInteger(PomTag.MOD_TIER, tier);
        return nbt;
    }
}
