package com.prosbloom.pom.factory;

import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CurrencyFactory {
    private List<ICurrency> currencies;

    public CurrencyFactory() {
        currencies = new ArrayList<ICurrency>();
        currencies.add(ModItems.chaosOrb);

        System.out.println("Currencies loaded:");
        currencies.forEach(c->System.out.println(c.getCurrencyName()));
    }

    // roll currency and reset currency nbt tag
    public void processCurrency(ItemStack stack) {
        // make sure we should be here
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(PomTag.CURRENCY_NAME)) {
            ICurrency currency = currencies.stream()
                    .filter(c->c.getCurrencyName().equals(stack
                            .getTagCompound()
                            .getString(PomTag.CURRENCY_NAME)))
                    .findFirst()
                    .orElse(null);
            currency.process(stack);
            // clear the tag
            stack.getTagCompound().removeTag(PomTag.CURRENCY_NAME);
        }
    }
}
