package com.prosbloom.pom.items;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.PrefixExistsException;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.items.interfaces.ICurrency;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO - Implement IModifier - save name of currency item to nbt on itemstack, playercontainerevent firing triggers a reroll of the item based on the currency item name
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
    public String getCurrencyName() {
        return this.getUnlocalizedName();
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Chaos Orbing: " + stack.getItem().getUnlocalizedName());
        try {
            Pom.itemFactory.addPrefix(stack);
        } catch (PrefixExistsException e) {
            System.out.println("Tried to add a prefix where one exists already");
        }

        return stack;
    }
}
