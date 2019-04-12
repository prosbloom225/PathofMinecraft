package com.prosbloom.pom.factory;

import com.google.common.collect.Multimap;
import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NbtHelper {
    // TODO - get these dynamically
    protected static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    public static void addModifiers(ItemStack stack, List<Modifier> mods) throws ModifierException{
        for (Modifier mod : mods)
            addModifier(stack, mod);
    }
    public static void addModifier(ItemStack stack, Modifier mod) throws ModifierException {
        PomItemData item = getNbt(stack);
        if (mod instanceof Prefix && item.getPrefixes().size() < PomTag.PREFIXES.length) {
            item.addModifier(mod);
        } else if (mod instanceof Suffix && item.getSuffixes().size() < PomTag.SUFFIXES.length) {
            item.addModifier(mod);
        }else
            throw new ModifierException();
        writeNbt(stack, item);
    }

    public static void clearPrefixes(ItemStack stack) {
        try {
            for (Prefix prefix : getPrefixes(stack)) {
                NbtHelper.clearModifier(stack, prefix);
            }
        } catch (ModifierException e) {
            System.out.println("No modifiers to clear..");
        }
    }
    public static void clearSuffixes(ItemStack stack) {
        try {
            for (Suffix suffix : getSuffixes(stack)) {
                NbtHelper.clearModifier(stack, suffix);
            }
        } catch (ModifierException e) {
            System.out.println("No modifiers to clear..");
        }
    }

    public static void clearModifier(ItemStack stack, Modifier mod) throws ModifierException{
        PomItemData item = getNbt(stack);
        for (Modifier modifier : item.getModifiers())
            if (mod.equals(modifier)) {
                item.getModifiers().remove(modifier);
                break;
            }
        // reserialize
        writeNbt(stack, item);
    }
    public static void clearModifiers(ItemStack stack) {
        PomItemData item = getNbt(stack);
        item.setModifiers(new ArrayList<Modifier>());
        writeNbt(stack, item);
    }

    // item level nbt
    public static PomItemData getNbt(ItemStack stack){
        // any nbt initialization can happen here
        PomItemData item = new PomItemData();
        // create the nbt and save if it doesnt exist
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.POM_TAG)) {
            item = new PomItemData(stack.getTagCompound().getCompoundTag(PomTag.POM_TAG));
        } else {
            NbtHelper.writeNbt(stack, item);
        }
        return item;
    }
    public static void writeNbt(ItemStack stack, PomItemData item) {
        NBTTagCompound nbt,pomNbt;
        if (stack.hasTagCompound())
            nbt = stack.getTagCompound();
        else
            nbt = new NBTTagCompound();
        pomNbt = item.serializeNbt();
        nbt.setTag(PomTag.POM_TAG, pomNbt);
        stack.setTagCompound(nbt);
    }
    public static boolean hasTag(ItemStack stack) {
        return stack.hasTagCompound()
                && stack.getTagCompound().hasKey(PomTag.POM_TAG);
    }

    public static boolean isDummy(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.DUMMY))
            return true;
        return false;
    }
    public static void setDummy(ItemStack stack, boolean dummy){
        getNbt(stack);
        stack.getTagCompound().setString(PomTag.DUMMY, "dummy");
    }
    public static LibMisc.Rarity getRarity(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getRarity();
    }
    public static void setRarity(ItemStack stack, LibMisc.Rarity rarity) {
        PomItemData item = getNbt(stack);
        item.setRarity(rarity);
        writeNbt(stack, item);
    }


    public static int getIlvl(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.ilvl;
        /*
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.ILVL))
            return stack.getTagCompound().getInteger(PomTag.ILVL);
        // default all items to ilvl 1, means we have to handle less exceptions
        // TODO - this is unsafe.  proceed with caution
        return 1;
        */

    }
    public static void setIlvl(ItemStack stack, int ilvl) {
        PomItemData item = getNbt(stack);
        item.ilvl = ilvl;
        writeNbt(stack, item);
    }


    public static List<Prefix> getPrefixes(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getPrefixes();
    }
    public static List<Suffix> getSuffixes(ItemStack stack) {
        PomItemData item = getNbt(stack);
        return item.getSuffixes();
    }

    public static List<Modifier> getModifiers(ItemStack stack) {
        return getNbt(stack).getModifiers();
    }

    // Attribute stuff
    public static void processItemData(ItemStack stack) {
        // check nbt here for performace
       if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.POM_TAG)) {
           PomItemData item = getNbt(stack);
           // clear the attributes
           stack.getTagCompound().removeTag("AttributeModifiers");
            // process mainhand attributes
            Multimap<String, AttributeModifier> modifiers = stack.getItem().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, stack);
            // Phys Dmg
            double dmgMod = 1.0;
           //TODO - can probably clean this up.. stolen code from old ModSword
            for (Prefix prefix : item.getPrefixes())
                dmgMod += prefix.getDamageMod();
            final Optional<AttributeModifier> prefixOptional = modifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()).stream()
                    .filter(attributeModifier -> attributeModifier.getID().equals(ATTACK_DAMAGE_MODIFIER))
                    .findFirst();
            if (prefixOptional.isPresent()) {
                double dmg = prefixOptional.get().getAmount() * dmgMod;
                AttributeModifier mod = new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon Modifier", dmg, 0);
                stack.addAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), mod, EntityEquipmentSlot.MAINHAND);
            }

            // Atk Spd
            double spdMod = 1.0;
            for (Suffix suffix : item.getSuffixes())
                spdMod += suffix.getSpeedMod();
            final Optional<AttributeModifier> suffixOptional = modifiers.get(SharedMonsterAttributes.ATTACK_SPEED.getName()).stream()
                    .filter(attributeModifier -> attributeModifier.getID().equals(ATTACK_SPEED_MODIFIER))
                    .findFirst();
            if (suffixOptional.isPresent()) {
                // spd calc is weird cuz it starts negative
                double val = suffixOptional.get().getAmount();
                double spd = val + Math.abs(val) * spdMod;
                AttributeModifier mod = new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon Modifier", spd, 0);
                stack.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getName(), mod, EntityEquipmentSlot.MAINHAND);
            }
        }
    }
}
