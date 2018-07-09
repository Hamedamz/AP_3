package models.Setting;

//import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException; fixme what is this?
import models.GameLogic.Exceptions.ValueNotFoundException;

import javax.imageio.IIOException;
import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public final class GameLogicConfig {
    private static Map<String, Integer> classProperties;

    private static final String CONSTANTS_SAVE_LOCATION = "src/class properties.txt";
    private static final String IGNORE_REGEX = "#.*";
    private static final String PROPERTY_FORMAT = "((\\D(_\\d+ )?)|\\s)+:\\s+\\d+(\\.\\d+)?";

    public static final String[] TROOPS = new String[]{"Archer", "Dragon", "Giant", "Guardian"};
    public static final String[] TOWERS = new String[]{"AirDefense", "ArcherTower", "Barracks", "Camp", "Cannon", "ElixirMine", "ElixirStorage", "GoldMine", "GoldStorage", "TownHall", "WizardTower"};


    public static Set<String> getClassPropertiesName() {
        return classProperties.keySet();
    }

    static {
        loadConstants();
    } // FIXME: 5/7/2018

    /**
     * lines starting with # are read as comments
     * <p>
     * it supports all kind of [sentence]/[number]
     * which sentence is sequence of Alphabetical letters or spaces
     * or [Alphabetical letter + "_" + noneNegetiveInteger]
     */
    public static void loadConstants() {
        classProperties = new HashMap<>();
        try {
            File constantFile = new File(CONSTANTS_SAVE_LOCATION);
            InputStream stream = new FileInputStream(constantFile);
            Scanner scanner = new Scanner(stream);
            while (scanner.hasNext()) {
                addToClassProperties(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("constant file not found");
        }
//        for (String str : classProperties.keySet()) {
//            System.err.println(str + " " + classProperties.get(str));
//        }
    }

    private static void addToClassProperties(String newLine) {
        newLine = newLine.replaceAll(IGNORE_REGEX, "");
        newLine = newLine.replaceAll("\\s+", " ");
        if (newLine.matches(PROPERTY_FORMAT)) {
            newLine = newLine.trim();
            String[] mapParts = newLine.split("\\s*:\\s*");
            if (mapParts.length == 2) {
                classProperties.put(mapParts[0], Integer.parseInt(mapParts[1]));
            }
        }

    }

    public static int getFromDictionary(String value) {
        try {
            return classProperties.get(value);
        } catch (Exception e) {
            System.out.println("Value " + value + " Not found: " + e.getLocalizedMessage());
            return 0;
        }
    }
}
