package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class Suffix extends Modifier {

        private float []speedModRange;
        private float speedMod = 0;

        public float[] getSpeedModRange() {
                return speedModRange;
        }
        public void setSpeedModRange(float[] speedModRange) {
                this.speedModRange = speedModRange;
        }
        public float getSpeedMod() {
                return speedMod;
        }
        public void setSpeedMod(float speedMod) {
                this.speedMod = speedMod;
        }

        @Override
        public NBTTagCompound serializeNbt() {
                NBTTagCompound nbt = super.serializeNbt();
                nbt.setFloat(PomTag.MOD_SPEEDRANGE_MIN, this.getSpeedModRange()[0]);
                nbt.setFloat(PomTag.MOD_SPEEDRANGE_MAX, this.getSpeedModRange()[1]);
                nbt.setFloat(PomTag.MOD_SPEEDMOD, speedMod);
                return nbt;
        }

        public Suffix(NBTTagCompound nbt){
                super(nbt);
                this.setSpeedMod(nbt.hasKey(PomTag.MOD_SPEEDMOD) ? nbt.getFloat(PomTag.MOD_SPEEDMOD) : 0);
                this.setSpeedModRange(new float[]{
                        nbt.hasKey(PomTag.MOD_SPEEDRANGE_MIN) ? nbt.getFloat(PomTag.MOD_SPEEDRANGE_MIN) : 0,
                        nbt.hasKey(PomTag.MOD_SPEEDRANGE_MAX) ? nbt.getFloat(PomTag.MOD_SPEEDRANGE_MAX) : 0});
        }

        @Override
        public String getAdvTooltip() {
                String t = super.getAdvTooltip();
                t += String.format(TextFormatting.BLUE + "%.2f(%.2f-%.2f) to attack speed\n" + TextFormatting.RESET,
                        (double)this.getSpeedMod(), (double)this.getSpeedModRange()[0], (double)this.getSpeedModRange()[1]);
                return t;
        }

    public boolean equals(Suffix other) {
        return (super.equals(other)
                && this.getSpeedMod() == other.getSpeedMod()
                && Arrays.equals(this.getSpeedModRange(), other.getSpeedModRange()));
    }
}
