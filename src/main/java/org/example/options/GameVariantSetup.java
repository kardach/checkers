package org.example.options;

import org.example.model.*;

import java.util.*;

import static java.util.Map.entry;

//public class GameVariantSetup {
//    private final SortedMap<String, Variant> variants = new TreeMap<>();
//
//    public GameVariantSetup() {
//        variants.putAll(Map.ofEntries(
//                entry("International/Polish", new Variant("International/Polish", 10,
//                        20, true, Variant.PiecesPlacement.ON_BLACK,
//                        Variant.FirstMove.WHITE, true, true, Variant.Capture.MAXIMUM,
//                        Variant.Crowning.ON_FINISH)),
//                entry("Ghanaian/damii", new Variant("Ghanaian/damii", 10, 20,
//                        false, Variant.PiecesPlacement.ON_WHITE, Variant.FirstMove.BLACK,
//                        true, true, Variant.Capture.ALL, Variant.Crowning.ON_FINISH))
//        ));
//        selected = variants.firstKey();
//    }
//
//    private String selected;
//
//    public void select(String name) {
//        selected = name;
//    }
//
//    public String getSelected() {
//        return selected;
//    }
//
//    public Variant getSelectedVariant() {
//        return variants.get(selected);
//    }
//
//    public String[] getVariantNames() {
//        return variants.keySet().toArray(new String[0]);
//    }
//}

public class GameVariantSetup {
    private final SortedMap<String, GameVariant> variants = new TreeMap<>();
    private String selected;
    private final GameDirector gameDirector;
    private final GameBuilder gameBuilder;

    public GameVariantSetup() {
        gameDirector = new GameDirector();
        gameBuilder = new GameBuilder();
        variants.putAll(Map.ofEntries(
                entry("International/Polish", GameVariant.INTERNATIONAL),
                entry("Ghanaian/damii", GameVariant.GHANAIAN)
        ));
        selected = variants.firstKey();
    }

    public String[] getVariantNames() {
        return variants.keySet().toArray(new String[0]);
    }

    public void select(String name) {
        selected = name;
    }

    public String getSelected() {
        return selected;
    }

    public Game getGame() {
        switch (variants.get(selected)) {
            case INTERNATIONAL -> gameDirector.constructInternationalGameVariant(gameBuilder);
            case GHANAIAN -> gameDirector.constructGhanaianGameVariant(gameBuilder);
        }
        return gameBuilder.build();
    }
}