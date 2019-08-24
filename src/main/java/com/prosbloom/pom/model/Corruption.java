package com.prosbloom.pom.model;

import com.prosbloom.pom.LibMisc;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;
import java.util.stream.Collectors;

import static com.prosbloom.pom.LibMisc.Types.SWORD;

public class Corruption extends Modifier {

    private Map<String, Integer> weight;
    private int[] flatDmgRangePhysicalMin;
    private int[] flatDmgRangePhysicalMax;

    public int getWeightForType(LibMisc.Types type){
        return weight.get(type.toString().toLowerCase());
    }
    public Corruption(NBTTagCompound nbt) {
        super(nbt);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.name, this.weight.entrySet().stream().map(w->w.getKey() + w.getValue()).collect(Collectors.joining(": ")));
    }
}
