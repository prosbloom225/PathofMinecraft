package com.prosbloom.pom.items;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.exception.ModifierExistsException;
import com.prosbloom.pom.exception.ModifierNotFoundException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.interfaces.ICurrency;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChaosOrb extends BaseItem implements ICurrency {
    @Override
    public String getBaseName() {
        return "chaosorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        // TODO - implement
        if (NbtHelper.getRarity(stack) == LibMisc.Rarity.RARE)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Chaos Orbing: " + stack.getItem().getUnlocalizedName());
        NbtHelper.clearPrefixes(stack);
        NbtHelper.clearSuffixes(stack);

        try {
            NbtHelper.addModifier(stack, Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)));
            NbtHelper.addModifier(stack, Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)));
            NbtHelper.addModifier(stack, Pom.itemFactory.rollSuffix(NbtHelper.getIlvl(stack)));
            NbtHelper.addModifier(stack, Pom.itemFactory.rollSuffix(NbtHelper.getIlvl(stack)));
        } catch (ModifierException e) {
            System.out.print("Somehow we tried to add a modifier when one exists: " + e.toString());
        }

        return stack;
    }
}
