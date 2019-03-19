package com.prosbloom.pom.model;

import java.util.Arrays;
import java.util.List;

public class Modifiers {

    public List<Modifier> getPrefixes() {
        List<Modifier> prefixes = Arrays.asList(this.prefixes);
        return prefixes;
    }

    private Modifier[] prefixes;
    private Modifier[] suffixes;

}
