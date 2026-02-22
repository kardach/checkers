package org.example.variants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class GameVariantSetup {
    private final Map<String, Variant> variants = Map.ofEntries(
            entry("International/Polish", new Variant(10, 20)),
            entry("Ghanaian/damii", new Variant(10, 20))
            );
    private String selected;

    public void select(String name) {
        selected = name;
    }

    public String getSelected() {
        return selected;
    }

    public Variant getSelectedVariant() {
        return variants.get(selected);
    }

    public String[] getVariantNames() {
        return variants.keySet().toArray(new String[0]);
    }
}
