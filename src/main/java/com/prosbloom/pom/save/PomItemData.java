package com.prosbloom.pom.save;

import com.prosbloom.pom.model.PomTag;
import com.prosbloom.pom.model.Prefix;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;

public class PomItemData {

    @Store
    public int ilvl;

    @Store
    public List<Prefix> prefixes;

    public PomItemData() {
        prefixes = new ArrayList<>();
        ilvl = 1;
    }
}
