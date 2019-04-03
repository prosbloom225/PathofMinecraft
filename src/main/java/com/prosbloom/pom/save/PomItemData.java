package com.prosbloom.pom.save;

import com.prosbloom.pom.model.Modifier;
import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class PomItemData {

    @Store
    public int ilvl;

    @Store
    public List<Modifier> modifiers;

    public PomItemData() {
        modifiers = new ArrayList<>();
        ilvl = 1;
    }

    public List<Prefix> getPrefixes() {
        // filter out the blanks
        return modifiers.stream()
                .filter(p -> p != null)
                .filter(Prefix.class::isInstance)
                .map(Prefix.class::cast)
                .collect(Collectors.toList());
    }
}
