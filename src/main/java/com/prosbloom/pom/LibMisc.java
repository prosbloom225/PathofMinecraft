package com.prosbloom.pom;

import java.util.Random;

public class LibMisc {

    public static final String MODID = "pom";
    public static final String MODNAME = "Path of Minecraft";
    public static final String BUILD = "GRADLE:BUILD";
    public static final String VERSION = "GRADLE:VERSION-" + BUILD;


    public enum Rarity {
        NORMAL,
        MAGIC,
        RARE,
        UNIQUE;

        public static Rarity getRandomRarity() {
            int pick = new Random().nextInt(Rarity.values().length);
            return Rarity.values()[pick];
        }
    }
    public enum Types {
        SWORD,
        BOW;
    }
}
