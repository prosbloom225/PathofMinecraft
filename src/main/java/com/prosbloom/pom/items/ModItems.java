package com.prosbloom.pom.items;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("pom:modsword")
    public static ModSword modSword;

    @GameRegistry.ObjectHolder("pom:itemcase")
    public static ItemCase itemCase;

    @GameRegistry.ObjectHolder("pom:chaosorb")
    public static ChaosOrb chaosOrb;

    @GameRegistry.ObjectHolder("pom:annulmentorb")
    public static AnnulmentOrb annulmentOrb;

    @GameRegistry.ObjectHolder("pom:divineorb")
    public static DivineOrb divineOrb;

    @GameRegistry.ObjectHolder("pom:scourorb")
    public static ScourOrb scourOrb;

    @GameRegistry.ObjectHolder("pom:exaltedorb")
    public static ExaltedOrb exaltedOrb;

    @GameRegistry.ObjectHolder("pom:alchemyorb")
    public static AlchemyOrb alchemyOrb;

    @GameRegistry.ObjectHolder("pom:alterationorb")
    public static AlterationOrb alterationOrb;

    @GameRegistry.ObjectHolder("pom:augmentorb")
    public static AugmentOrb augmentOrb;

    @GameRegistry.ObjectHolder("pom:regalorb")
    public static RegalOrb regalOrb;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        modSword.initModel();
        itemCase.initModel();
        chaosOrb.initModel();
        annulmentOrb.initModel();
        divineOrb.initModel();
        scourOrb.initModel();
        exaltedOrb.initModel();
        alchemyOrb.initModel();
        alterationOrb.initModel();
        augmentOrb.initModel();
        regalOrb.initModel();
    }

}

