package com.prosbloom.pom.items;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlchemyOrb extends BaseItem implements ICurrency {
    private static int minMods = 4;
    public String getBaseName() {
        return "alchemyorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        if (NbtHelper.getRarity(stack) == LibMisc.Rarity.NORMAL)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        NbtHelper.clearModifiers(stack);
        NbtHelper.setRarity(stack, LibMisc.Rarity.RARE);
        List<Modifier> prefixes = new ArrayList<>();
        List<Modifier> suffixes = new ArrayList<>();
        // get between 4 and 6 random mods, with a random amnt of suffixes/prefixes, not going above max for each type
        int maxMods = new Random().nextInt(PomTag.PREFIXES.length + PomTag.SUFFIXES.length - minMods + 1) + minMods;
        // TODO - this is slightly inefficient but not gamebreaking... might roll a few randoms for no reason
        for (int i = 0; i < maxMods; i++)
            if (new Random().nextBoolean() && prefixes.size() < PomTag.PREFIXES.length)
                prefixes.add(Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)));
            else if (suffixes.size() < PomTag.SUFFIXES.length)
                suffixes.add(Pom.itemFactory.rollSuffix(NbtHelper.getIlvl(stack)));
            else
                i--;

        System.out.println(String.format("Alchemy Orbing: %d:%d mods", prefixes.size(), suffixes.size()));
        try {
            if (prefixes.size() > 0)
                NbtHelper.addModifiers(stack, prefixes);
            if (suffixes.size() > 0)
                NbtHelper.addModifiers(stack, suffixes);
            } catch (ModifierException e){
                System.out.println("Error in adding modifier: " + e.toString());
            }
        return stack;
    }


}
