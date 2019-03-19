package com.prosbloom.pom.items;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("pom:modsword")
    public static ModSword modSword;

    @GameRegistry.ObjectHolder("pom:itemcase")
    public static ItemCase itemCase;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        modSword.initModel();
        itemCase.initModel();
    }

}

