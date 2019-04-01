package com.prosbloom.pom.items.currency;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.exception.ModifierExistsException;
import com.prosbloom.pom.exception.ModifierNotFoundException;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChaosOrb extends BaseItem implements ICurrency {
    public static final int minMods = 4;
    @Override
    public String getBaseName() {
        return "chaosorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        // TODO - implement
        if (NbtHelper.getRarity(stack) == LibMisc.Rarity.RARE)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        NbtHelper.clearModifiers(stack);
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
        System.out.println(String.format("Chaos Orbing: %s - %d:%d mods", stack.getItem().getUnlocalizedName(), prefixes.size(), suffixes.size()));
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
