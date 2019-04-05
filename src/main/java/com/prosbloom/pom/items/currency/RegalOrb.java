package com.prosbloom.pom.items.currency;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.interfaces.ICurrency;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class RegalOrb extends BaseItem implements ICurrency {
    public String getBaseName() {
        return "regalorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        // is magic
        if (NbtHelper.getRarity(stack) == LibMisc.Rarity.MAGIC)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println(String.format("Regal Orbing: %s", stack.getItem().getRegistryName()));
        NbtHelper.setRarity(stack, LibMisc.Rarity.RARE);
        try {
            if (new Random().nextBoolean())
                NbtHelper.addModifier(stack, Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)));
            else
                NbtHelper.addModifier(stack, Pom.itemFactory.rollSuffix(NbtHelper.getIlvl(stack)));
        } catch (ModifierException e) {
            System.out.println("Error in adding modifier: " + e.toString());
        }
        return stack;
    }


}
