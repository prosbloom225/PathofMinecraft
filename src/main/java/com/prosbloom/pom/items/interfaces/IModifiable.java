package com.prosbloom.pom.items.interfaces;

import com.prosbloom.pom.model.Modifier;

import java.util.List;

public interface IModifiable {
    void addPrefix(Modifier mod);
    void addSuffix(Modifier mod);

    void addModifier(Modifier mod);
    boolean removeModifier(Modifier mod);
    List<Modifier> getModifiers();

    void refreshMods();
}
