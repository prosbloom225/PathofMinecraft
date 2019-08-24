package com.prosbloom.pom.test;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.items.currency.*;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.walkers.ItemStackData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestCurrency {
    static Prefix pfx;
    static Suffix sfx;
    static ItemStack stack;
    static PomItemData item;

    @BeforeAll
    public static void init() {
        Bootstrap.register();
        ModItems.init();
        Pom.itemFactory = new ItemFactory();
    }

    @BeforeEach
    public void setup() {
        pfx = Pom.itemFactory.rollPrefix(99);
        sfx = Pom.itemFactory.rollSuffix(99);
        stack = new ItemStack(new ModSword());
        item = new PomItemData();
        NbtHelper.writeNbt(stack, item);
    }

    @Test
    public void testChaosOrb() {
        ChaosOrb chaosOrb  = new ChaosOrb();
        NbtHelper.setRarity(stack, LibMisc.Rarity.RARE);
        chaosOrb.process(stack);
        int size = NbtHelper.getModifiers(stack).size();
        Assertions.assertTrue(size >= 3);
        Assertions.assertTrue(size <= 6);
    }

    @Test
    public void testAlchemyOrb() {
        AlchemyOrb alchemyOrb = new AlchemyOrb();
        NbtHelper.setRarity(stack, LibMisc.Rarity.NORMAL);
        NbtHelper.clearModifiers(stack);
        alchemyOrb.process(stack);
        Assertions.assertEquals(LibMisc.Rarity.RARE, NbtHelper.getRarity(stack));
        int size = NbtHelper.getModifiers(stack).size();
        Assertions.assertTrue(size >= 3);
        Assertions.assertTrue(size <= 6);
    }

    @Test
    public void testAlterationOrb() throws ModifierException {
        AlterationOrb alterationOrb = new AlterationOrb();
        NbtHelper.clearModifiers(stack);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, sfx);
        NbtHelper.setRarity(stack, LibMisc.Rarity.MAGIC);
        PomItemData item = NbtHelper.getNbt(stack);
        alterationOrb.process(stack);
        Assertions.assertTrue(!item.equals(NbtHelper.getNbt(stack)));
        Assertions.assertTrue(NbtHelper.getModifiers(stack).size() >= 1);
        Assertions.assertTrue(NbtHelper.getModifiers(stack).size() <= 2);
    }

    @Test
    public void testAnnulmentOrb() throws ModifierException {
        NbtHelper.clearModifiers(stack);
        NbtHelper.addModifier(stack, pfx);
        new AnnulmentOrb().process(stack);
        Assertions.assertEquals(0, NbtHelper.getModifiers(stack).size());

        NbtHelper.clearModifiers(stack);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, sfx);
        new AnnulmentOrb().process(stack);
        Assertions.assertEquals(2, NbtHelper.getModifiers(stack).size());
    }

    @Test
    public void testAugmentOrb() throws ModifierException {
        NbtHelper.clearModifiers(stack);
        NbtHelper.setRarity(stack, LibMisc.Rarity.MAGIC);
        NbtHelper.addModifier(stack, pfx);
        new AugmentOrb().process(stack);
        Assertions.assertEquals(2, NbtHelper.getModifiers(stack).size());

        NbtHelper.clearModifiers(stack);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, sfx);
        new AugmentOrb().process(stack);
        Assertions.assertEquals(2, NbtHelper.getModifiers(stack).size());

    }

    @Test
    public void testDivineOrb() throws ModifierException {
        NbtHelper.clearModifiers(stack);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, sfx);
        float pp = NbtHelper.getPrefixes(stack).get(0).getDamageMod();
        float ss = NbtHelper.getSuffixes(stack).get(0).getSpeedMod();
        new DivineOrb().process(stack);
        Assertions.assertNotEquals(pp, NbtHelper.getPrefixes(stack).get(0).getDamageMod());
        Assertions.assertNotEquals(ss, NbtHelper.getSuffixes(stack).get(0).getSpeedMod());
    }

    @Test
    public void testExaltedOrb() throws ModifierException {
        NbtHelper.clearModifiers(stack);
        new ExaltedOrb().process(stack);
        Assertions.assertEquals(1, NbtHelper.getModifiers(stack).size());

        NbtHelper.clearModifiers(stack);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, pfx);
        new ExaltedOrb().process(stack);
        Assertions.assertEquals(3, NbtHelper.getModifiers(stack).size());

        NbtHelper.clearModifiers(stack);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, sfx);
        NbtHelper.addModifier(stack, sfx);
        NbtHelper.addModifier(stack, sfx);
        new ExaltedOrb().process(stack);
        Assertions.assertEquals(6, NbtHelper.getModifiers(stack).size());
        //Assertions.assertThrows(ModifierException.class, () ->
         //   new ExaltedOrb().process(stack));
    }

    @Test
    public void testRegalOrb() {
        NbtHelper.clearModifiers(stack);
        NbtHelper.setRarity(stack, LibMisc.Rarity.MAGIC);
        new RegalOrb().process(stack);
        Assertions.assertEquals(1, NbtHelper.getModifiers(stack).size());
        Assertions.assertEquals(LibMisc.Rarity.RARE, NbtHelper.getRarity(stack));
    }

    @Test
    public void testScourOrb() throws ModifierException {
        NbtHelper.clearModifiers(stack);
        NbtHelper.addModifier(stack, pfx);
        new ScourOrb().process(stack);
        Assertions.assertEquals(0, NbtHelper.getModifiers(stack).size());
    }

    @Test
    public void testTransmutationOrb() {
        NbtHelper.clearModifiers(stack);
        NbtHelper.setRarity(stack, LibMisc.Rarity.NORMAL);
        new TransmutationOrb().process(stack);
        Assertions.assertTrue(NbtHelper.getModifiers(stack).size() > 0);
        Assertions.assertEquals(LibMisc.Rarity.MAGIC, NbtHelper.getRarity(stack));
    }

    @Test
    public void testKalandraMirror() {
        NbtHelper.clearModifiers(stack);
        NbtHelper.setRarity(stack, LibMisc.Rarity.NORMAL);
        ItemStack stack2 = stack.copy();
        new KalandraMirror().process(stack);
        Assertions.assertTrue(NbtHelper.getMirrored(stack));
        Assertions.assertNotEquals(stack2, stack);
    }

    @Test
    public void testVaalOrb() {
        NbtHelper.clearModifiers(stack);
        NbtHelper.setRarity(stack, LibMisc.Rarity.NORMAL);
        ItemStack stack2 = stack.copy();
        new VaalOrb().process(stack);
        Assertions.assertTrue(NbtHelper.getCorrupted(stack));
        Assertions.assertNotEquals(stack2, stack);
    }
}
