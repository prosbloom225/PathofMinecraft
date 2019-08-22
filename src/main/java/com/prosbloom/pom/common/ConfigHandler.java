package com.prosbloom.pom.common;

import com.prosbloom.pom.LibMisc;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sun.util.resources.ar.CurrencyNames_ar_YE;

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

    // drops
    public static float currencyDropRate =  0.20f;
    public static float transmutationOrbDropRate = 0.20831f;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);
        config.load();
        load();
        buildregex();
    }

    public static void load() {
        String desc;

        // General
        desc = "Should vanilla minecraft weapons be added to system and receive pom modifiers.";
        addVanillaWeaponsToSystem = config.getBoolean("addVanillaWeaponsToSystem.enabled", "general", true, desc);

        desc = "Should non-pom, modded items be added to system and receive pom modifiers.  This will generally work with most items that use default minecraft AttributeModifiers.";
        addOtherModItemsToSystem = config.getBoolean("addOtherModItemsToSystem.enabled", "general", true, desc);

        // Weapon
        desc = "Regex that specifies an item will receive sword modifiers";
        swordRegex = config.getString("swordRegex", "weapon", ".*sword.*", desc);


        // Drops
        desc = "Chance any killed mob will drop a currency item";
        currencyDropRate = config.getFloat("currencyDropRate", "drops", currencyDropRate, 0, 1, desc);

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
