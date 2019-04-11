package com.prosbloom.pom.items;

import com.google.common.collect.Multimap;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.client.gui.GuiScreen;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModSword extends ItemSword {
    protected String baseName = "modsword";

    public ModSword() {
        super(ToolMaterial.STONE);
        setRegistryName(Pom.MODID, baseName);
        setTranslationKey(Pom.MODID + "." + baseName);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    /*
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {

        PomItemData item = NbtHelper.getNbt(stack);
        tooltip.add("(" + item.ilvl + ")");
        String suffix;
        List<String> advPrefix = new ArrayList<>();
        List<String> advSuffix = new ArrayList<>();
        List<Prefix> prefixes;
        List<Suffix> suffixes;

        // TODO - naming item on first modifiers
        String prefix = "";
        for (Prefix p : item.getPrefixes()){
            if (p != null) {
                prefix = p.getName();
                break;
            }
        }

        suffixes = NbtHelper.getSuffixes(stack);
        if (suffixes.size() > 0)
            suffix = suffixes.get(0).getName();
        else
            suffix = "";
        String name;
        switch (NbtHelper.getRarity(stack)) {
            case NORMAL:
                name = TextFormatting.WHITE + "";
                break;
            case MAGIC:
                name = TextFormatting.BLUE+ "";
                break;
            case RARE:
                name = TextFormatting.YELLOW+ "";
                break;
            case UNIQUE:
                name = TextFormatting.RED+ "";
                break;
            default:
                name = TextFormatting.WHITE+ "";
        }
        tooltip.add(String.format("%s %s %s %s", name, prefix, baseName, suffix));
        if (GuiScreen.isShiftKeyDown()) {
            for (Prefix p : item.getPrefixes())
                advPrefix.add(p.getAdvTooltip());
            suffixes.stream().forEach(s -> advSuffix.add(s.getAdvTooltip()));
            tooltip.addAll(advPrefix);
            tooltip.addAll(advSuffix);
        }

    }
     */

    /*
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        List<Prefix> prefixes = NbtHelper.getPrefixes(stack);
        List<Suffix> suffixes = NbtHelper.getSuffixes(stack);
        // Weapons
        if (slot == EntityEquipmentSlot.MAINHAND) {
            // Process prefixes
            if (prefixes.size() > 0) {
                // Phys Dmg
                double dmgMod = 1.0;
                for (Prefix prefix : prefixes)
                    dmgMod += prefix.getDamageMod();
                final Optional<AttributeModifier> modifierOptional = modifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()).stream()
                        .filter(attributeModifier -> attributeModifier.getID().equals(ATTACK_DAMAGE_MODIFIER))
                        .findFirst();
                if (modifierOptional.isPresent()) {
                    final AttributeModifier modifier = modifierOptional.get();
                    double dmg = modifierOptional.get().getAmount() * dmgMod;
                    modifiers.remove(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), modifier);
                    modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                            new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", dmg, 0));
                }
            }
            // Suffix
            if (suffixes.size() > 0) {
                // Atk Spd
                double spdMod = 1.0;
                for (Suffix suffix : suffixes)
                    spdMod += suffix.getSpeedMod();
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
            }
        }

        return modifiers;
    }
     */
}
