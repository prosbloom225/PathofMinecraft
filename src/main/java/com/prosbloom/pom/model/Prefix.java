package com.prosbloom.pom.model;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

@Storable
public class Prefix extends Modifier {

    @Store
    public float damageMod = 0;
    @Store
    public float[] damageModRange;

    public Prefix(String name, int ilvl, int tier, float[] damageModRange, float damageMod) {
        super(name, ilvl, tier);
        this.damageModRange = damageModRange;
        this.damageMod = damageMod;
    }


    public void setDamageMod(float damageMod) {
        this.damageMod = damageMod;
    }
    public void setDamageModRange(float[] damageModRange) {
        this.damageModRange = damageModRange;
    }
    public float getDamageMod() {
        return damageMod;
    }
    public float[] getDamageModRange() {
        return damageModRange;
    }


    @Override
    public NBTTagCompound toNbt() {
        NBTTagCompound nbt = super.toNbt();
        nbt.setFloat(PomTag.MOD_DAMAGEMOD, damageMod);
        nbt.setFloat(PomTag.MOD_DAMAGERANGE_MIN, damageModRange[0]);
        nbt.setFloat(PomTag.MOD_DAMAGERANGE_MAX, damageModRange[1]);
        return nbt;
    }

    public Prefix(NBTTagCompound nbt) {
        super(nbt);
        this.setDamageMod(nbt.hasKey(PomTag.MOD_DAMAGEMOD ) ? nbt.getFloat(PomTag.MOD_DAMAGEMOD) : 0);
        this.setDamageModRange(new float[]{
                nbt.hasKey(PomTag.MOD_DAMAGERANGE_MIN) ? nbt.getFloat(PomTag.MOD_DAMAGERANGE_MIN) : 0,
                nbt.hasKey(PomTag.MOD_DAMAGERANGE_MAX) ? nbt.getFloat(PomTag.MOD_DAMAGERANGE_MAX) : 0});
    }

    @Override
    public String getAdvTooltip() {
        String t = super.getAdvTooltip();
        t += String.format(TextFormatting.BLUE + "%.2f(%.2f-%.2f) to damage\n" + TextFormatting.RESET,
                (double)this.getDamageMod(), (double)this.getDamageModRange()[0], (double)this.getDamageModRange()[1]);
        return t;
    }

    public boolean equals(Prefix other) {
        return (super.equals(other)
                && this.damageMod == other.getDamageMod()
                && Arrays.equals(this.getDamageModRange(), other.getDamageModRange()));
    }
}
