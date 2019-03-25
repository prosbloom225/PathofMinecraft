package com.prosbloom.pom.factory;

import com.prosbloom.pom.model.PomTag;
import net.minecraft.item.ItemStack;

public class CurrencyFactory {
    // roll currency and reset currency nbt tag
    public void processCurrency(ItemStack stack) {

        // make sure we should be here
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.CURRENCY_NAME)) {
            String currency = stack.getTagCompound().getString(PomTag.CURRENCY_NAME);
            System.out.println("Processing currency: " + currency + " on: " + stack.getItem().getUnlocalizedName());
        }
    }
}
