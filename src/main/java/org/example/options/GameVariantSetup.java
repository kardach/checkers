package org.example.options;

import java.util.Map;

import static java.util.Map.entry;

public class GameVariantSetup {
    private final Map<String, Variant> variants = Map.ofEntries(
            entry("International/Polish", new Variant("International/Polish", 10,
                    20, true, Variant.PiecesPlacement.ON_BLACK, Variant.FirstMove.WHITE)),
            entry("Ghanaian/damii", new Variant("Ghanaian/damii", 10, 20,
                    false, Variant.PiecesPlacement.ON_WHITE, Variant.FirstMove.BLACK))
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
