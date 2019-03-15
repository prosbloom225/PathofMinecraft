package com.prosbloom.pom.items;

import com.prosbloom.pom.items.interfaces.IModifiable;
import com.prosbloom.pom.model.Modifier;
import net.minecraft.item.ItemSword;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModSword extends ItemSword implements IModifiable {
    private String baseName = "ModSword";
    private Modifier prefix;
    private Modifier suffix;
    private List<Modifier> modifiers = new ArrayList();
    private float []damagePhysical;


    public ModSword() {
        super(ToolMaterial.STONE);
        damagePhysical = new float[2];
        setUnlocalizedName(baseName);
    }
    public void addPrefix(Modifier mod) {
        this.prefix = mod;
        refreshMods();
    }
    public void addSuffix(Modifier mod) {
        this.suffix = mod;
        refreshMods();
    }

    public void addModifier(Modifier mod) {
        modifiers.add(mod);
    }
    public boolean removeModifier(Modifier mod) {
        return modifiers.remove(mod);
    }
    public List<Modifier> getModifiers() {
        return this.modifiers;
    }

    @Override
    public float getAttackDamage() {
        Random r = new Random();
        float physMod =damagePhysical[0] + r.nextFloat() * (damagePhysical[1] - damagePhysical[0]);
        return super.getAttackDamage() * (1 + physMod);
    }

    public void refreshMods() {
        System.out.println("Refreshing mods for item: " + baseName);
        String name = new String(baseName);

        if (prefix != null) {
            name = prefix.getName() + " " + name;
            damagePhysical[0] += prefix.getDamageMod()[0];
            damagePhysical[1] += prefix.getDamageMod()[1];
        }
        if (suffix != null) {
            name = name + " " + suffix.getName();
        }
        this.setUnlocalizedName(name);

        for (Modifier mod : modifiers) {
            damagePhysical[0]+=mod.getDamageMod()[0];
            damagePhysical[1]+=mod.getDamageMod()[1];
        }
    }
}
