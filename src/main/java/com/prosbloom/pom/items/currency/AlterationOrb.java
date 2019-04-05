package com.prosbloom.pom.items.currency;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

public class AlterationOrb extends BaseItem implements ICurrency {
    @Override
    public String getBaseName() {
        return "alterationorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        if (NbtHelper.getRarity(stack) == LibMisc.Rarity.MAGIC)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Alting: " + stack.getItem().getRegistryName());
        NbtHelper.clearModifiers(stack);
        try {
            while (NbtHelper.getModifiers(stack).size() == 0) {
                if (new Random().nextBoolean())
                    NbtHelper.addModifier(stack, Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)));
                if (new Random().nextBoolean())
                    NbtHelper.addModifier(stack, Pom.itemFactory.rollSuffix(NbtHelper.getIlvl(stack)));
            }
        } catch (ModifierException e) {
            System.out.println("Error in adding modifier: " + e.toString());
        }

        return stack;
    }
}
