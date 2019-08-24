package com.prosbloom.pom.items.currency;

import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.Modifier;
import net.minecraft.item.ItemStack;

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
        System.out.println("Annulling: " + stack.getItem().getRegistryName());
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

    public boolean shouldConsume() {
        return true;
    }
}
