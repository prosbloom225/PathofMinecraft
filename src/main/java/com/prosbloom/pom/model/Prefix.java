package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public class Prefix extends Modifier {

    private float damageMod = 0;
    private float[] damageModRange;

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
}
