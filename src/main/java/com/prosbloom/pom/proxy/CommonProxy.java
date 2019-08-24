package com.prosbloom.pom.proxy;

import com.prosbloom.pom.events.MobDeathEvents;
import com.prosbloom.pom.events.PomEvents;
import com.prosbloom.pom.items.BaseItem;
import com.prosbloom.pom.items.ModItems;
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
    public static MobDeathEvents mobDeathEvents = new MobDeathEvents();

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
        ModItems.init();
        for (BaseItem item : ModItems.getItems())
            event.getRegistry().register(item);
    }
}
