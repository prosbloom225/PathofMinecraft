package com.prosbloom.pom.proxy;

import com.prosbloom.pom.events.PomEvents;
import com.prosbloom.pom.items.*;
import com.prosbloom.pom.items.currency.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static PomEvents pomEvents = new PomEvents();

    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ModSword());
        event.getRegistry().register(new ModBow());
        event.getRegistry().register(new ItemCase());
        event.getRegistry().register(new ChaosOrb());
        event.getRegistry().register(new AnnulmentOrb());
        event.getRegistry().register(new DivineOrb());
        event.getRegistry().register(new ScourOrb());
        event.getRegistry().register(new ExaltedOrb());
        event.getRegistry().register(new AlchemyOrb());
        event.getRegistry().register(new AlterationOrb());
        event.getRegistry().register(new AugmentOrb());
        event.getRegistry().register(new RegalOrb());
    }
}
