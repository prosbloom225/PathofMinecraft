package com.prosbloom.pom.items.currency;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.interfaces.ICurrency;
import net.minecraft.item.ItemStack;

import static com.prosbloom.pom.Pom.corruptionFactory;

public class VaalOrb extends BaseItem implements ICurrency {
    public String getBaseName() {
        return "vaalorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        // cant mirror a mirrored item
        if (NbtHelper.getCorrupted(stack))
            return false;
        return true;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        NbtHelper.setCorrupted(stack, true);
        stack = Pom.corruptionFactory.corruptItem(stack);
        // TODO - corruptions, maybe load from json config
        return stack;
    }

    public boolean shouldConsume() {
        return true;
    }


}
