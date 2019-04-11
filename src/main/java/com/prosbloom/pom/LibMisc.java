package com.prosbloom.pom;

import java.util.Random;

public class LibMisc {
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
