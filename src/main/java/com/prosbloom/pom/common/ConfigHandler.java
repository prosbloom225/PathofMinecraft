package com.prosbloom.pom.common;

import com.prosbloom.pom.LibMisc;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;

    public static boolean testProperty = true;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);
        config.load();
        load();
    }

    public static void load() {
        String desc;

        desc = "this is a test property";
        testProperty = config.getBoolean("testProp.enabled", "general", true, desc);

        if(config.hasChanged())
            config.save();
    }

    public static void loadPostInit() {
        if(config.hasChanged())
            config.save();
    }

    @Mod.EventBusSubscriber(modid = LibMisc.MODID)
    public static class ChangeListener {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
            if(eventArgs.getModID().equals(LibMisc.MODID))
                load();
        }

    }

}
