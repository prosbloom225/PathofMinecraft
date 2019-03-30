package com.prosbloom.pom.items;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.exception.ModifierNotFoundException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.Modifier;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import static com.prosbloom.pom.LibMisc.Rarity.NORMAL;

public class ScourOrb extends Item implements ICurrency {

    public ScourOrb() {
        super();
        setUnlocalizedName("scourorb");
        setRegistryName(Pom.MODID, "scourorb");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        // TODO - implement
        if (NbtHelper.getRarity(stack) == LibMisc.Rarity.RARE || NbtHelper.getRarity(stack) == LibMisc.Rarity.MAGIC)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Scouring: " + stack.getItem().getUnlocalizedName());
        List<Modifier> mods = NbtHelper.getModifiers(stack);
        if (mods.size() > 0)
            NbtHelper.clearModifiers(stack);
        NbtHelper.setRarity(stack, NORMAL);
        return stack;
    }
}
