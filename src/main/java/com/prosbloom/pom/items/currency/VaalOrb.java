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

public class KalandraMirror extends BaseItem implements ICurrency {
    public String getBaseName() {
        return "kalandramirror";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        // cant mirror a mirrored item
        if (NbtHelper.getMirrored(stack))
            return false;
        return true;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        NbtHelper.setMirrored(stack, true);
        return stack;
    }

    public boolean shouldConsume() {
        return false;
    }


}
