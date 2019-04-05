package com.prosbloom.pom.save;

import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PomItemData {

    public int ilvl;

    /*
    public List<Prefix> prefixes;
    public List<Suffix> suffixes;
     */
    // TODO - move this shit back to list, will save so many null checks despite complicating adding prefix orders
    public Prefix []prefixes;
    public Suffix []suffixes;

    public PomItemData() {
        //prefixes = new ArrayList<>();
        //suffixes = new ArrayList<>();
        prefixes = new Prefix[PomTag.PREFIXES.length];
        suffixes = new Suffix[PomTag.SUFFIXES.length];
        ilvl = 1;
    }

    public List<Prefix> getPrefixes() {
        return Arrays.asList(this.prefixes);
    }
    /*
    public List<Prefix> getPrefixes() {
        // filter out the blanks
        return modifiers.stream()
                .filter(p -> p != null)
                .filter(Prefix.class::isInstance)
                .map(Prefix.class::cast)
                .collect(Collectors.toList());
    }
     */

    public NBTTagCompound serializeNbt() {
        NBTTagCompound pom = new NBTTagCompound();
        pom.setInteger(PomTag.ILVL, ilvl);
        for (int i=0;i<PomTag.PREFIXES.length;i++)
            if (prefixes[i] != null)
                pom.setTag(PomTag.PREFIXES[i], prefixes[i].serializeNbt());
        for (int i=0;i<PomTag.SUFFIXES.length;i++)
            if (suffixes[i] != null)
                pom.setTag(PomTag.SUFFIXES[i], suffixes[i].serializeNbt());

        return pom;
    }

    // deserialize nbt
    public PomItemData (NBTTagCompound pom) {
        prefixes = new Prefix[PomTag.PREFIXES.length];
        suffixes = new Suffix[PomTag.SUFFIXES.length];
        this.ilvl = pom.getInteger(PomTag.ILVL);
        for (int i=0;i<PomTag.PREFIXES.length;i++)
            if (pom.hasKey(PomTag.PREFIXES[i]))
                prefixes[i] = new Prefix(pom.getCompoundTag(PomTag.PREFIXES[i]));
        for (int i=0;i<PomTag.SUFFIXES.length;i++)
            if (pom.hasKey(PomTag.SUFFIXES[i]))
                suffixes[i] = new Suffix(pom.getCompoundTag(PomTag.SUFFIXES[i]));

    }

    public boolean equals(PomItemData other) {
        boolean ret = true;
        ret &= this.ilvl == other.ilvl;
        for(int i=0; i < prefixes.length; i++)
            if (prefixes[i] != null)
                ret &= this.prefixes[i].equals(other.prefixes[i]);
        for(int i=0; i < suffixes.length; i++)
            if (suffixes[i] != null)
                ret &= this.suffixes[i].equals(other.suffixes[i]);
        return ret;
    }
}
