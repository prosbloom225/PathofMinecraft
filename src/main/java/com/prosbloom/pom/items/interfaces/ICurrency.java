package com.prosbloom.pom.items.interfaces;

import net.minecraft.item.ItemStack;

public interface ICurrency {
    String getCurrencyName();
    ItemStack process(ItemStack stack);
}
