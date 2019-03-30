package com.prosbloom.pom.items;

import com.ibm.icu.impl.ICUResourceBundle;
import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.PomTag;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class AlchemyOrb extends BaseItem implements ICurrency {
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
        for (int i = 0; i <= 4 + new Random().nextInt(PomTag.PREFIXES.length + PomTag.SUFFIXES.length - 4); i++)
            try {
                NbtHelper.addModifier(stack, Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)));
            } catch (ModifierException e){
                System.out.println("Error in adding modifier: " + e.toString());
            }
        return stack;
    }


}
