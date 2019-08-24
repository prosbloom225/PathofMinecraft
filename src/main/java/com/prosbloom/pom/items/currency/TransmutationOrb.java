package com.prosbloom.pom.items.currency;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
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

import static com.prosbloom.pom.LibMisc.Rarity.*;

public class TransmutationOrb extends BaseItem implements ICurrency {
    public String getBaseName() {
        return "transmutationorb";
    }


    @Override
    public boolean canProcess(ItemStack stack) {
        // TODO - implement
        if (NbtHelper.getRarity(stack) == NORMAL)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Transmuting: " + stack.getItem().getRegistryName());
        // last check for modifiers
        if (NbtHelper.getModifiers(stack).size() < 2){
            NbtHelper.setRarity(stack, MAGIC);
            // add corresponding prefix/suffix based
            try {
                if (new Random().nextBoolean())
                    NbtHelper.addModifier(stack, Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)));
                if (new Random().nextBoolean())
                    NbtHelper.addModifier(stack, Pom.itemFactory.rollSuffix(NbtHelper.getIlvl(stack)));
            } catch (ModifierException e) {
                System.out.println("Error in adding modifier: " + e.toString());
            }
        }
        return stack;
    }

    public boolean shouldConsume() {
        return true;
    }
}
