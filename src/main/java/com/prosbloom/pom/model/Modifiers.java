package com.prosbloom.pom.model;

import java.util.Arrays;
import java.util.List;

public class Modifiers {

    public List<Modifier> getPrefixes() {
        List<Modifier> prefixes = Arrays.asList(this.prefixes);
        return prefixes;
    }
    public List<Modifier> getSuffixes() {
        List<Modifier> suffixes = Arrays.asList(this.suffixes);
        return suffixes;
    }

    private Prefix[] prefixes;
    private Suffix[] suffixes;

}
