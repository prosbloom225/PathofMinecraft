package com.prosbloom.pom.test;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.DropFactory;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.items.ModItems;
import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestDropFactory {

    @BeforeAll
    public static void init() {
        Bootstrap.register();
        ModItems.init();
        Pom.itemFactory = new ItemFactory();
        Pom.dropFactory = new DropFactory();
    }

    @Test
    public void testWeightedCurrency() {
        Assertions.assertNotNull(Pom.dropFactory.generateWeightedCurrency());
        for (int i = 0; i < 100; i++)
            Assertions.assertNotEquals("baseitem", Pom.dropFactory.generateWeightedCurrency().getBaseName());
        //System.out.println(Pom.dropFactory.generateWeightedCurrency().getBaseName());
    }

    @Test
    public void testWeightedDrops() {
        Assertions.assertNotNull(Pom.dropFactory.generateWeightedDrops(1, 99));
        Assertions.assertEquals(3, Pom.dropFactory.generateWeightedDrops(3, 99).size());
        Assertions.assertEquals(99, Pom.dropFactory.generateWeightedDrops(99, 99).size());

        for (int i = 0; i < 100; i++)
            Assertions.assertNotEquals("pom:baseitem", Pom.dropFactory.generateWeightedDrops(1, 99).get(0).getItem().getRegistryName());
        //System.out.println(Pom.dropFactory.generateWeightedDrops(1, 99).get(0).getItem().getRegistryName());
    }
}
