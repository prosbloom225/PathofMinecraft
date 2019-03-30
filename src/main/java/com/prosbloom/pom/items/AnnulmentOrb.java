package com.prosbloom.pom.items;

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

public class AnnulmentOrb extends BaseItem implements ICurrency {

    @Override
    public String getBaseName() {
        return "annulmentorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        if (NbtHelper.getModifiers(stack).size() >= 1)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Annulling: " + stack.getItem().getUnlocalizedName());
        List<Modifier> mods = NbtHelper.getModifiers(stack);
        if (mods.size() > 0)
            try {
                Modifier mod = mods.get(new Random().nextInt(mods.size()));
                NbtHelper.clearModifier(stack, mod);
            } catch (ModifierException e) {
                System.out.println("Modifier not found: " + e.toString());
            }
        return stack;
    }
}
