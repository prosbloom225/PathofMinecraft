package com.prosbloom.pom.test;

import com.prosbloom.pom.LibMisc;
import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.model.*;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestNbtHelper {
    static Prefix pfx;
    static Suffix sfx;
    static ItemStack stack;

    @BeforeAll
    public static void init() {
        Bootstrap.register();
        Pom.itemFactory = new ItemFactory();
        pfx = Pom.itemFactory.rollPrefix(99);
        sfx = Pom.itemFactory.rollSuffix(99);
    }
    @BeforeEach
    public void setup() {
        stack = new ItemStack(new ModSword());
    }
    @Test
    public void testItemStructSerialization() {
        PomItemData item = new PomItemData(), item2;
        item.ilvl = 88;
        item.rarity = LibMisc.Rarity.RARE;
        item.getModifiers().add(pfx);
        item.getModifiers().add(sfx);
        NBTTagCompound itm = item.serializeNbt();
        item2 = new PomItemData(itm);
        Assertions.assertTrue(item.equals(item2));
    }

    @Test
    public void TestNbtDeserialization() {
        PomItemData item = new PomItemData(), item2;
        item.ilvl = 88;
        item.rarity = LibMisc.Rarity.RARE;
        item.getModifiers().add(pfx);
        item.getModifiers().add(sfx);
        item.setMirrored(true);
        NBTTagCompound itm = item.serializeNbt();
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setTag(PomTag.POM_TAG, item.serializeNbt());
        item2 = new PomItemData(stack.getTagCompound().getCompoundTag(PomTag.POM_TAG));
        Assertions.assertTrue(item.equals(item2));

    }

    @Test
    public void TestNbtHelperAddPrefix() throws ModifierException {
        NbtHelper.addModifier(stack, pfx);
        // prefix
        List<Prefix> prefixes = NbtHelper.getPrefixes(stack);
        Assertions.assertEquals(1, prefixes.size());
        Assertions.assertTrue(prefixes.get(0).equals(pfx));

    }
    @Test
    public void TestNbtHelperAddSuffix() throws ModifierException {
        NbtHelper.addModifier(stack, sfx);
        // suffix
        List<Suffix> suffixes = NbtHelper.getSuffixes(stack);
        Assertions.assertEquals(1, suffixes.size() );
        Assertions.assertTrue(suffixes.get(0).equals(sfx));
    }
    @Test
    public void TestNbtHelperAddStaticModifiers() throws ModifierException {
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, sfx);
        NbtHelper.addModifier(stack, sfx);
        NbtHelper.addModifier(stack, sfx);
        // prefix
        List<Prefix> prefixes = NbtHelper.getPrefixes(stack);
        Assertions.assertEquals(3, prefixes.size());

        // suffix
        List<Suffix> suffixes = NbtHelper.getSuffixes(stack);
        Assertions.assertEquals(3, suffixes.size() );

    }

    @Test
    public void TestNbtHelperAddModifiersMultiple() throws ModifierException {
        int num_tests = 10;
        for (int i =0; i < num_tests; i++) {
            NbtHelper.clearModifiers(stack);
            int num_prefixes = new Random().nextInt(3);
            int num_suffixes = new Random().nextInt(3);
            List<Modifier> modifiers = new ArrayList<>();
            for (int p = 0; p < num_prefixes; p++)
                modifiers.add(Pom.itemFactory.rollPrefix(99));
            for (int s = 0; s < num_suffixes; s++)
                modifiers.add(Pom.itemFactory.rollSuffix(99));
            NbtHelper.addModifiers(stack, modifiers);
            Assertions.assertEquals(num_prefixes + num_suffixes, NbtHelper.getModifiers(stack).size());
        }

    }

    @Test
    public void testClearModifiers() throws ModifierException {
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.clearModifiers(stack);
        Assertions.assertEquals(0, NbtHelper.getModifiers(stack).size());
    }
}
