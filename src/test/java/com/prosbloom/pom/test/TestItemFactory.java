package com.prosbloom.pom.test;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.items.ModItems;
import com.prosbloom.pom.model.Prefix;
import com.prosbloom.pom.model.Suffix;
import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestItemFactory {
    static Prefix pfx;
    static Suffix sfx;

    @BeforeAll
    public static void setup() {
        Bootstrap.register();
        Pom.itemFactory = new ItemFactory();
        pfx = Pom.itemFactory.rollPrefix(99);
        sfx = Pom.itemFactory.rollSuffix(99);
    }

    @Test
    public void testRandomModifiers() {
        Assertions.assertNotNull(pfx);
        Assertions.assertNotNull(sfx);
    }

    @Test
    public void testFindModifiers() {
        Assertions.assertNotNull(Pom.itemFactory.getPrefix(pfx.getName()));
        Assertions.assertNotNull(Pom.itemFactory.getSuffix(sfx.getName()));
    }

}
