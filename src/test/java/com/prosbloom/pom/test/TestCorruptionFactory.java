package com.prosbloom.pom.test;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.CorruptionFactory;
import com.prosbloom.pom.factory.ItemFactory;
import com.prosbloom.pom.items.ModItems;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.prosbloom.pom.LibMisc.Types.*;

public class TestCorruptionFactory {
    private ItemStack stack;

    @BeforeAll
    public static void init() {
        Bootstrap.register();
        ModItems.init();
        Pom.itemFactory = new ItemFactory();
        Pom.corruptionFactory = new CorruptionFactory();
    }
    @BeforeEach
    public void setup() {
        stack = new ItemStack(new ItemSword(Item.ToolMaterial.DIAMOND));
        Pom.itemFactory.generateItem(stack, 99, SWORD);
    }

    @Test
    public void testCorruptionLoading() {
        Assertions.assertNotNull(Pom.corruptionFactory.generateWeightedCorruption(stack));
        //System.out.println(Pom.dropFactory.generateWeightedCurrency().getBaseName());
    }
}
