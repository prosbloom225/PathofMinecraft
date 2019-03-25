package com.prosbloom.pom.proxy;

import com.prosbloom.pom.Pom;
import com.prosbloom.pom.events.PomEvents;
import com.prosbloom.pom.factory.CurrencyFactory;
import com.prosbloom.pom.items.ChaosOrb;
import com.prosbloom.pom.items.ItemCase;
import com.prosbloom.pom.items.ModSword;
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
        Pom.currencyFactory = new CurrencyFactory();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ModSword());
        event.getRegistry().register(new ItemCase());
        event.getRegistry().register(new ChaosOrb());
    }
}
