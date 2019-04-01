package com.prosbloom.pom.items.currency;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AugmentOrb extends BaseItem implements ICurrency {
    public String getBaseName() {
        return "augmentorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        // is magic and has open suffix || prefix
        if (NbtHelper.getRarity(stack) == LibMisc.Rarity.MAGIC &&
                NbtHelper.getModifiers(stack).size() == 1)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println(String.format("Augment Orbing: %s", stack.getItem().getUnlocalizedName()));
        // add corresponding prefix/suffix based
        try {
            if (NbtHelper.getPrefixes(stack).size() > 0)
                NbtHelper.addModifier(stack, Pom.itemFactory.rollSuffix(NbtHelper.getIlvl(stack)));
            else
                NbtHelper.addModifier(stack, Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)));
        } catch (ModifierException e) {
            System.out.println("Error in adding modifier: " + e.toString());
        }
        return stack;
    }


}
