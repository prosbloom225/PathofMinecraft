package com.prosbloom.pom.common;

import com.prosbloom.pom.LibMisc;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.regex.Pattern;

public class ConfigHandler {
    public static Configuration config;

    // general
    public static boolean addVanillaWeaponsToSystem = true;
    public static boolean addOtherModItemsToSystem = true;

    // weapon
    public static String swordRegex;
    public static Pattern swordRegexPattern;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);
        config.load();
        load();
        buildregex();
    }

    public static void load() {
        String desc;

        desc = "Should vanilla minecraft weapons be added to system and receive pom modifiers.";
        addVanillaWeaponsToSystem = config.getBoolean("addVanillaWeaponsToSystem.enabled", "general", true, desc);

        desc = "Should non-pom, modded items be added to system and receive pom modifiers.  This will generally work with most items that use default minecraft AttributeModifiers.";
        addOtherModItemsToSystem = config.getBoolean("addOtherModItemsToSystem.enabled", "general", true, desc);

        desc = "Regex that specifies an item will receive sword modifiers";
        swordRegex = config.getString("swordRegex", "weapon", ".*sword.*", desc);


        if(config.hasChanged())
            config.save();
    }

    private static void buildregex() {
        swordRegexPattern  = Pattern.compile(swordRegex);
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
