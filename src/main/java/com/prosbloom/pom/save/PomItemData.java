package com.prosbloom.pom.save;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PomItemData {
    public int ilvl;
    private List<Modifier> modifiers;
    public LibMisc.Rarity rarity;
    // TODO - need to save type of item as well.
    // especially for nonmod items added to system, might want to have a bow with sword stats or whatever
    // then again that miht break shit.  either way, store the type...


    public int getIlvl() {
        return ilvl;
    }
    public void setIlvl(int ilvl) {
        this.ilvl = ilvl;
    }
    public LibMisc.Rarity getRarity() {
        return rarity;
    }
    public void setRarity(LibMisc.Rarity rarity) {
        this.rarity = rarity;
    }
    public void setModifiers(List modifiers) {
        this.modifiers = modifiers;
    }
    public List<Modifier> getModifiers() {
        return modifiers;
    }
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
    }


    public PomItemData() {
        modifiers = new ArrayList<>();
        ilvl = 1;
        rarity = LibMisc.Rarity.NORMAL;
    }

    public List<Prefix> getPrefixes() {
        // filter out the blanks
        return modifiers.stream()
                .filter(p -> p != null)
                .filter(Prefix.class::isInstance)
                .map(Prefix.class::cast)
                .collect(Collectors.toList());
    }
    public List<Suffix> getSuffixes() {
        // filter out the blanks
        return modifiers.stream()
                .filter(p -> p != null)
                .filter(Suffix.class::isInstance)
                .map(Suffix.class::cast)
                .collect(Collectors.toList());
    }

    public NBTTagCompound serializeNbt() {
        NBTTagCompound pom = new NBTTagCompound();
        pom.setInteger(PomTag.ILVL, ilvl);
        pom.setString(PomTag.RARITY, rarity.toString());
        int p=0, s=0;
        for (Modifier modifier : modifiers) {
            if (modifier instanceof Prefix && p < PomTag.PREFIXES.length) {
                pom.setTag(PomTag.PREFIXES[p], modifier.serializeNbt());
                p++;
            } else if (modifier instanceof Suffix && s < PomTag.SUFFIXES.length) {
                pom.setTag(PomTag.SUFFIXES[s], modifier.serializeNbt());
                s++;
            } else {
                System.out.println("Could not add modifier" + modifier.toString());
            }
        }
        return pom;
    }

    // deserialize nbt
    public PomItemData (NBTTagCompound pom) {
        modifiers = new ArrayList<>();
        this.ilvl = pom.getInteger(PomTag.ILVL);
        this.rarity = LibMisc.Rarity.valueOf(pom.getString(PomTag.RARITY));
        for (int i=0;i<PomTag.PREFIXES.length;i++)
            if (pom.hasKey(PomTag.PREFIXES[i]))
                modifiers.add(new Prefix(pom.getCompoundTag(PomTag.PREFIXES[i])));
        for (int i=0;i<PomTag.SUFFIXES.length;i++)
            if (pom.hasKey(PomTag.SUFFIXES[i]))
                modifiers.add(new Suffix(pom.getCompoundTag(PomTag.SUFFIXES[i])));

    }

    public boolean equals(PomItemData other) {
        boolean ret = true;
        ret &= this.ilvl == other.ilvl;
        ret &= this.rarity == other.rarity;
        for (Modifier modifier : modifiers) {
            ret &= other.getModifiers().contains(modifier);
        }
        return ret;
    }
}
