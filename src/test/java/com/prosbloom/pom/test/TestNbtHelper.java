package com.prosbloom.pom.test;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.exception.ModifierException;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.factory.NbtHelper;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.items.ModSword;
import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        item.getModifiers().add(pfx);
        item.getModifiers().add(sfx);
        NBTTagCompound itm = item.serializeNbt();
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setTag(PomTag.POM_TAG, item.serializeNbt());
        item2 = new PomItemData(stack.getTagCompound().getCompoundTag(PomTag.POM_TAG));
        Assertions.assertTrue(item.equals(item2));

    }

    @Test
    public void TestNbtHelperAddModifiers() throws ModifierException {
        NbtHelper.addModifier(stack, pfx);
        NbtHelper.addModifier(stack, sfx);
        // prefix
        List<Prefix> prefixes = NbtHelper.getPrefixes(stack);
        Assertions.assertEquals(1, prefixes.size());
        Assertions.assertTrue(prefixes.get(0).equals(pfx));

        // suffix
        List<Suffix> suffixes = NbtHelper.getSuffixes(stack);
        Assertions.assertEquals(1, suffixes.size() );
        Assertions.assertTrue(suffixes.get(0).equals(sfx));

    }
}
