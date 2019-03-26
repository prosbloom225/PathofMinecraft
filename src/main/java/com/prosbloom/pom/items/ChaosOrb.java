package com.prosbloom.pom.items;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierExistsException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.interfaces.ICurrency;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChaosOrb extends Item implements ICurrency {
    public ChaosOrb() {
        super();
        setUnlocalizedName("chaosorb");
        setRegistryName(Pom.MODID, "chaosorb");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Chaos Orbing: " + stack.getItem().getUnlocalizedName());
        NbtHelper.clearPrefixes(stack);
        NbtHelper.clearSuffixes(stack);

        try {
            Pom.itemFactory.addPrefix(stack);
            Pom.itemFactory.addSuffix(stack);
        } catch (ModifierExistsException e) {
            System.out.println("Tried to add a prefix where one exists already");
        }

        return stack;
    }
}
