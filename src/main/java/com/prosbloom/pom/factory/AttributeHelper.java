package com.prosbloom.pom.factory;

import com.google.common.collect.Multimap;
import com.prosbloom.pom.model.*;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.Optional;

import static com.prosbloom.pom.factory.NbtHelper.*;

public class AttributeHelper {

    private static double getWeaponDmgMod(PomItemData itemData) {
        // Phys Dmg
        double dmgMod = 1.0;
        for (Prefix prefix: itemData.getPrefixes())
            dmgMod += prefix.getDamageMod();
        return dmgMod;
    }
    private static double getWeaponDmgFlat(PomItemData itemData) {
        // Phys Dmg
        double dmg = 0.0;
        for (Prefix prefix: itemData.getPrefixes())
            dmg += prefix.getDamageMod();
        if (itemData.getCorruption() != null)
            dmg += itemData.getCorruption().getFlatDmgAvg();
        return dmg;
    }

    public static void processItemData(ItemStack stack) {
        // check nbt here for performace
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.POM_TAG)) {
            PomItemData item = getNbt(stack);
            // clear the attributes
            stack.getTagCompound().removeTag("AttributeModifiers");
            // process mainhand attributes
            Multimap<String, AttributeModifier> modifiers = stack.getItem().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, stack);
            final Optional<AttributeModifier> prefixOptional = modifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()).stream()
                    .filter(attributeModifier -> attributeModifier.getID().equals(ATTACK_DAMAGE_MODIFIER))
                    .findFirst();
            if (prefixOptional.isPresent()) {
                double dmg = (prefixOptional.get().getAmount() * getWeaponDmgMod(item)) + getWeaponDmgFlat(item);
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
