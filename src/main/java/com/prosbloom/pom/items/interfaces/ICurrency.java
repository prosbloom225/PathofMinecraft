package com.prosbloom.pom.items.interfaces;

import net.minecraft.item.ItemStack;

public interface ICurrency {
    ItemStack process(ItemStack stack);
    boolean canProcess(ItemStack stack);
}
