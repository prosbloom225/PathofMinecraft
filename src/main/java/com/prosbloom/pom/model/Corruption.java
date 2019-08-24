package com.prosbloom.pom.model;

import com.prosbloom.pom.LibMisc;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class Corruption extends Modifier {

    private double rate;
    private Map<String, Integer> weight;
    private int[] flatDmgRangePhysicalMin;
    private int[] flatDmgRangePhysicalMax;

    public Corruption(NBTTagCompound nbt) {
        super(nbt);
    }

    @Override
    public String toString() {
        return String.format("%s: %.5f", this.name, this.rate);
    }
}
