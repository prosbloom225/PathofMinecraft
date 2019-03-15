package com.prosbloom.pom.model;

public class Modifier {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getIlvl() {
        return ilvl;
    }

    public void setIlvl(int ilvl) {
        this.ilvl = ilvl;
    }

    private int ilvl;
    private int tier;
    private float[] damageMod;

    public float[] getDamageMod() {
        return damageMod;
    }

    public void setDamageMod(float[] damageMod) {
        this.damageMod = damageMod;
    }


    @Override
    public String toString() {
        return String.format("T%s-%s(%s)", tier,name, ilvl);
    }
}
