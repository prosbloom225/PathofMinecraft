package com.prosbloom.pom.test;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import com.prosbloom.pom.save.PomItemData;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestNbtHelper {
    static Prefix pfx;
    static Suffix sfx;
    static ItemStack stack;

    @BeforeAll
    public static void setup() {
        Bootstrap.register();
        Pom.itemFactory = new ItemFactory();
        pfx = Pom.itemFactory.rollPrefix(99);
        sfx = Pom.itemFactory.rollSuffix(99);
        stack = new ItemStack(ModItems.modSword);
    }
    @Test
    public void testItemStructSerialization() {
        PomItemData item = new PomItemData(), item2;
        item.ilvl = 88;
        item.prefixes[0] = pfx;
        item.suffixes[0] = sfx;
        NBTTagCompound itm = item.serializeNbt();
        item2 = new PomItemData(itm);
        Assertions.assertTrue(item.equals(item2));
    }

    @Test
    public void TestNbtDeserialization() {
        PomItemData item = new PomItemData(), item2;
        item.ilvl = 88;
        item.prefixes[0] = pfx;
        item.suffixes[0] = sfx;
        NBTTagCompound itm = item.serializeNbt();
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setTag(PomTag.POM_TAG, item.serializeNbt());
        item2 = new PomItemData(stack.getTagCompound().getCompoundTag(PomTag.POM_TAG));
        Assertions.assertTrue(item.equals(item2));

    }
}
