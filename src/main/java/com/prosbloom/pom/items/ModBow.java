package com.prosbloom.pom.items;

import com.google.common.collect.Multimap;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
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

public class ModBow extends ItemBow {
    protected String baseName = "modbow";

    public ModBow() {
        super();
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

        tooltip.add("(" + NbtHelper.getIlvl(stack) + ")");
        String prefix, suffix;
        List<String> advPrefix = new ArrayList<>();
        List<String> advSuffix = new ArrayList<>();
        List<Prefix> prefixes;
        List<Suffix> suffixes;

        // TODO - naming item on first modifiers
        prefixes = NbtHelper.getPrefixes(stack);
        if (prefixes.size() > 0)
            prefix = prefixes.get(0).getName();
        else
            prefix = "";

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
            prefixes.stream().forEach(p -> advPrefix.add(p.getAdvTooltip()));
            suffixes.stream().forEach(s -> advSuffix.add(s.getAdvTooltip()));
            tooltip.addAll(advPrefix);
            tooltip.addAll(advSuffix);
        }

    }

}
