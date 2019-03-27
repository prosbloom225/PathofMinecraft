package com.prosbloom.pom.model;

import net.minecraft.nbt.NBTTagCompound;

public class Suffix extends Modifier {

        private float []speedModRange;

        public void setSpeedMod(float speedMod) {
                this.speedMod = speedMod;
        }

        private float speedMod = 0;

        public float[] getSpeedModRange() {
                return speedModRange;
        }

        public NBTTagCompound toNbt() {
                NBTTagCompound nbt = super.toNbt();
                nbt.setFloat(PomTag.MOD_SPEEDRANGE_MIN, this.getSpeedModRange()[0]);
                nbt.setFloat(PomTag.MOD_SPEEDRANGE_MAX, this.getSpeedModRange()[1]);
                nbt.setFloat(PomTag.MOD_SPEEDMOD, speedMod);
                return nbt;
        }
}
