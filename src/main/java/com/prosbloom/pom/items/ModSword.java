package com.prosbloom.pom.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.items.interfaces.IModifiable;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
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

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.PREFIX)) {
            if (stack.getTagCompound().getCompoundTag(PomTag.PREFIX).hasKey(PomTag.MOD_NAME)) {
                String prefix = stack.getTagCompound().getCompoundTag(PomTag.PREFIX).getString(PomTag.MOD_NAME);
                System.out.println("gotPrefix: " + prefix);
                tooltip.add(TextFormatting.WHITE + prefix + " " + TextFormatting.RESET + baseName);
            }
        }
        tooltip.add(TextFormatting.GRAY + "" + getAttackDamage() + " Damage ");
    }

    // deprecated
    public void refreshMods(ItemStack stack) {
        System.out.println("Refreshing mods for item: " + baseName);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        // Maybe should remove attributes, but wipe them for now
        //Multimap<String, AttributeModifier> modifiers = super.getItemAttributeModifiers(slot);
        Multimap<String, AttributeModifier> modifiers = HashMultimap.<String, AttributeModifier>create();
        // Weapons
        if (slot == EntityEquipmentSlot.MAINHAND) {
            // load the prefix - we only need the dmg right now, but preparing for more modifiers
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.PREFIX)) {
                Modifier prefix = Pom.itemFactory.getPrefix(stack.getTagCompound().getCompoundTag(PomTag.PREFIX).getString(PomTag.MOD_NAME));
                System.out.println("getAttributeModifiers prefixName: " + prefix.getName());
            }
            // Process prefixes
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.PREFIX)) {
                // Phys Dmg
                if (stack.getTagCompound().getCompoundTag(PomTag.PREFIX).hasKey(PomTag.MOD_DAMAGEMOD)) {
                    double dmg = stack.getTagCompound().getCompoundTag(PomTag.PREFIX).getFloat(PomTag.MOD_DAMAGEMOD);
                    modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", dmg, 0));
                }
                // Atk Sp
                if (stack.getTagCompound().getCompoundTag(PomTag.PREFIX).hasKey(PomTag.MOD_SPEEDMOD)) {
                    double spd = stack.getTagCompound().getCompoundTag(PomTag.PREFIX).getFloat(PomTag.MOD_SPEEDMOD);
                    modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", spd, 0));
                }
            }
        }
        return modifiers;
    }

}
