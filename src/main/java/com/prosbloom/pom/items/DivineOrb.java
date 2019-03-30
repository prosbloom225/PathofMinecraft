package com.prosbloom.pom.items;

import com.prosbloom.pom.Pom;
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

public class DivineOrb extends Item implements ICurrency {

    public DivineOrb() {
        super();
        setUnlocalizedName("divineorb");
        setRegistryName(Pom.MODID, "divineorb");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        if (NbtHelper.getModifiers(stack).size() > 0)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Divining: " + stack.getItem().getUnlocalizedName());
        List<Modifier> mods = NbtHelper.getModifiers(stack);
        if (mods.size() > 0)
            try {
                Pom.itemFactory.rerollPrefix(stack);
                Pom.itemFactory.rerollSuffix(stack);
            } catch (Exception e) {
                System.out.println("Modifier not found: " + e.toString());
            }
        return stack;
    }
}
