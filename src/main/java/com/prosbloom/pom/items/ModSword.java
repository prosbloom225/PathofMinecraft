package com.prosbloom.pom.items;

import com.google.common.collect.Multimap;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierNotFoundException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.interfaces.IModifiable;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ModSword extends ItemSword implements IModifiable {
    protected String baseName = "modsword";

    public ModSword() {
        super(ToolMaterial.STONE);
        setRegistryName(Pom.MODID, baseName);
        setUnlocalizedName(baseName);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {

        tooltip.add(TextFormatting.BLUE + "(" + NbtHelper.getIlvl(stack) + ")");
        String prefix, suffix;
        try {
            prefix = NbtHelper.getPrefix(stack).getName();
        } catch (ModifierNotFoundException e){
            prefix = "";
        }
        try {
            suffix = NbtHelper.getSuffix(stack).getName();
        } catch (ModifierNotFoundException e){
            suffix = "";
        }
        tooltip.add(
                TextFormatting.WHITE + prefix + " " +
                        TextFormatting.RESET + baseName + " " +
                        TextFormatting.WHITE + suffix);


    }

    // deprecated
    public void refreshMods(ItemStack stack) {
        System.out.println("Refreshing mods for item: " + baseName);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        // Weapons
        if (slot == EntityEquipmentSlot.MAINHAND) {
            // Process prefixes
            if (NbtHelper.hasPrefix(stack))
                try {
                    Prefix prefix = NbtHelper.getPrefix(stack);
                    // Phys Dmg
                    double dmgMod = (double)prefix.getDamageMod();
                    final Optional<AttributeModifier> modifierOptional = modifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()).stream()
                            .filter(attributeModifier -> attributeModifier.getID().equals(ATTACK_DAMAGE_MODIFIER))
                            .findFirst();
                    if (modifierOptional.isPresent()) {
                        final AttributeModifier modifier = modifierOptional.get();
                        double dmg = modifierOptional.get().getAmount()  * dmgMod;
                        modifiers.remove(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), modifier);
                        modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                                new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", dmg, 0));
                    }
                } catch (ModifierNotFoundException e) {
                    System.out.println("Couldnt find modifier, though one exists: " + e.toString());
                }
            // Suffix
            if (NbtHelper.hasSuffix(stack))
                try {
                    Suffix suffix = NbtHelper.getSuffix(stack);
                    // Atk Spd
                    double spdMod = suffix.getSpeedMod();
                    final Optional<AttributeModifier> modifierOptional = modifiers.get(SharedMonsterAttributes.ATTACK_SPEED.getName()).stream()
                            .filter(attributeModifier -> attributeModifier.getID().equals(ATTACK_SPEED_MODIFIER))
                            .findFirst();
                    if (modifierOptional.isPresent()) {
                        final AttributeModifier modifier = modifierOptional.get();

                        // spd calc is weird cuz it starts negative
                        double val = modifierOptional.get().getAmount();
                        double spd = val + Math.abs(val) * spdMod;
                        modifiers.remove(SharedMonsterAttributes.ATTACK_SPEED.getName(), modifier);
                        modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                                new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", spd, 0));
                    }
                } catch (ModifierNotFoundException e) {
                    System.out.println("Couldnt find modifier, though one exists: " + e.toString());
                }
        }
        return modifiers;
    }

}
