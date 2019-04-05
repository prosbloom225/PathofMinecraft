package com.prosbloom.pom.items.currency;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.exception.ModifierExistsException;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.interfaces.ICurrency;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class ExaltedOrb extends BaseItem implements ICurrency {
    @Override
    public String getBaseName() {
        return "exaltedorb";
    }

    @Override
    public boolean canProcess(ItemStack stack) {
        int modCount = NbtHelper.getModifiers(stack).size();
        if (modCount < (PomTag.PREFIXES.length + PomTag.SUFFIXES.length) && NbtHelper.getRarity(stack) == LibMisc.Rarity.RARE)
            return true;
        return false;
    }

    @Override
    public ItemStack process(ItemStack stack) {
        System.out.println("Exalting: " + stack.getItem().getRegistryName());
        List<Modifier> mods = NbtHelper.getModifiers(stack);
        // last check for modifier space to add a mod
        if (NbtHelper.getPrefixes(stack).size() < PomTag.PREFIXES.length ||
                NbtHelper.getSuffixes(stack).size() < PomTag.SUFFIXES.length) {
            try {
                boolean redo = true;
                Modifier mod = null;
                while (redo == true) {
                    // add a random modifier without preferring prefix or suffix
                    mod = new Random().nextBoolean() ?
                            Pom.itemFactory.rollPrefix(NbtHelper.getIlvl(stack)) :
                            Pom.itemFactory.rollSuffix(NbtHelper.getIlvl(stack));
                    if ((mod instanceof Prefix && NbtHelper.getPrefixes(stack).size() < PomTag.PREFIXES.length) ||
                            (mod instanceof Suffix && NbtHelper.getSuffixes(stack).size() < PomTag.SUFFIXES.length))
                        redo = false;
                }
                if (mod != null)
                    NbtHelper.addModifier(stack, mod);
                else
                    throw new ModifierException();
            } catch (ModifierException e) {
                System.out.println("Modifier not found: " + e.toString());
            }
        }
        return stack;
    }
}
