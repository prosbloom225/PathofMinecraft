package com.prosbloom.pom.test;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import info.loenwind.autosave.Reader;
import info.loenwind.autosave.Writer;
import net.minecraft.init.Bootstrap;
import net.minecraft.nbt.NBTTagCompound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestItemFactory {
    @BeforeAll
    public static void setup() {
        Bootstrap.register();
        Pom.itemFactory = new ItemFactory();
    }

    @Test
    public void testRandomModifiers() {
        Prefix pfx = Pom.itemFactory.rollPrefix(99);
        Assertions.assertNotNull(pfx);
        Suffix sfx = Pom.itemFactory.rollSuffix(99);
        Assertions.assertNotNull(sfx);
    }

    @Test
    public void testNbtStoringModifiers() {
        Prefix pfx = Pom.itemFactory.rollPrefix(99);
        NBTTagCompound nbt = new NBTTagCompound();
        Prefix item = Pom.itemFactory.rollPrefix(99);
        Writer.write(nbt, pfx);
        Reader.read(nbt, item);
        Assertions.assertTrue(pfx.equals(item));
    }
}
