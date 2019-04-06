package com.prosbloom.pom.test;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.factory.ItemFactory;
import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.BeforeAll;

public class TestMinecraft {

    @BeforeAll
    public static void init() {
        Bootstrap.register();
        Pom.itemFactory = new ItemFactory();

    }
}
