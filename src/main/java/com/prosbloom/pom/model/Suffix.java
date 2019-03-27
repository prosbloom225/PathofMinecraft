package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTTagCompound;

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

        public NBTTagCompound toNbt() {
                NBTTagCompound nbt = super.toNbt();
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
}
