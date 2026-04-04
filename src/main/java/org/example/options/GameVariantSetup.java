package org.example.options;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.Map.entry;

public class GameVariantSetup {
    private final SortedMap<String, Variant> variants = new TreeMap<>();

    public GameVariantSetup() {
        variants.putAll(Map.ofEntries(
                entry("International/Polish", new Variant("International/Polish", 10,
                        20, true, Variant.PiecesPlacement.ON_BLACK,
                        Variant.FirstMove.WHITE, true, true, Variant.Capture.MAXIMUM,
                        Variant.Crowning.ON_FINISH)),
                entry("Ghanaian/damii", new Variant("Ghanaian/damii", 10, 20,
                        false, Variant.PiecesPlacement.ON_WHITE, Variant.FirstMove.BLACK,
                        true, true, Variant.Capture.ALL, Variant.Crowning.ON_FINISH))
        ));
        selected = variants.firstKey();
    }

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
