package com.prosbloom.pom.items;

import com.prosbloom.pom.items.currency.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static List<BaseItem> getItems() {
        return items;
    }

    private static List<BaseItem> items;

    @GameRegistry.ObjectHolder("pom:modsword")
    public static ModSword modSword;

    @GameRegistry.ObjectHolder("pom:modbow")
    public static ModBow modBow;

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

    @GameRegistry.ObjectHolder("pom:transmutationorb")
    public static TransmutationOrb transmutationOrb;

    @GameRegistry.ObjectHolder("pom:kalandramirror")
    public static KalandraMirror kalandraMirror;

    @GameRegistry.ObjectHolder("pom:vaalorb")
    public static VaalOrb vaalOrb;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        for (BaseItem item : items)
            item.initModel();
    }
    public static void register(BaseItem item){
        items.add(item);
    }
    public static void init() {
        items = new ArrayList<>();
        modSword = new ModSword();
        modBow = new ModBow();
        itemCase = new ItemCase();
        chaosOrb = new ChaosOrb();
        annulmentOrb = new AnnulmentOrb();
        divineOrb = new DivineOrb();
        scourOrb = new ScourOrb();
        exaltedOrb = new ExaltedOrb();
        alchemyOrb = new AlchemyOrb();
        alterationOrb = new AlterationOrb();
        augmentOrb = new AugmentOrb();
        regalOrb = new RegalOrb();
        transmutationOrb = new TransmutationOrb();
        kalandraMirror = new KalandraMirror();
        vaalOrb= new VaalOrb();
    }
}

