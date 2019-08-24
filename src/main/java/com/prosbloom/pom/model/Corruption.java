package com.prosbloom.pom.model;

import com.prosbloom.pom.LibMisc;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;
import java.util.stream.Collectors;

import static com.prosbloom.pom.LibMisc.Types.SWORD;

public class Corruption extends Modifier {

    private Map<String, Integer> weight;
    private int[] flatDmgRangePhysicalMin;
    private int[] flatDmgRangePhysicalMax;

    // once rolled
    private int flatDmgMin = 0;
    private int flatDmgMax = 0;

    public int getWeightForType(LibMisc.Types type){
        return weight.get(type.toString().toLowerCase());
    }

    public Corruption(NBTTagCompound nbt) {
        super(nbt);
        this.flatDmgMin = nbt.hasKey(PomTag.MOD_NAME) ? nbt.getInteger(PomTag.MOD_DAMAGERANGE_MIN) : 0;
        this.flatDmgMax = nbt.hasKey(PomTag.MOD_NAME) ? nbt.getInteger(PomTag.MOD_DAMAGERANGE_MAX) : 0;
    }
    @Override
    public NBTTagCompound serializeNbt() {
        NBTTagCompound nbt = super.serializeNbt();
        nbt.setInteger(PomTag.MOD_DAMAGERANGE_MIN, flatDmgMin);
        nbt.setInteger(PomTag.MOD_DAMAGERANGE_MIN, flatDmgMax);
        return nbt;
        }

    public ItemStack corrupt(ItemStack stack) {
        // TODO - corruption logic goes here... or maybe in nbtHelper.processItem
        // kind of depends on how we want to apply these modifiers
        return stack;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.name, this.weight.entrySet().stream().map(w->w.getKey() + w.getValue()).collect(Collectors.joining(": ")));
    }

    @Override
    public boolean equals(Object obj) {
        Corruption other = (Corruption)obj;
        return (super.equals(other)
                && this.flatDmgMin == other.flatDmgMin
                && this.flatDmgMax == other.flatDmgMax
        );
    }
}
