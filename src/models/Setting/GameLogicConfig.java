package models.Setting;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import models.GameLogic.Exceptions.ValueNotFoundException;

import javax.imageio.IIOException;
import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class GameLogicConfig {
    private static Map<String, Number> classProperties;

    private static final String CONSTANTS_SAVE_LOCATION = "src\\class properties.txt";
    private static final String IGNORE_REGEX = "#.*";
    private static final String PROPERTY_FORMAT = "((\\D(_\\d+ )?)|\\s)+:\\s+\\d+(\\.\\d+)?";

    /**
     * lines starting with # are read as comments
     *
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
    }

    private static void addToClassProperties(String newLine) {
        if (!newLine.matches(IGNORE_REGEX)) {
            newLine = newLine.replaceAll("\\s+", " ");
            if(newLine.matches(PROPERTY_FORMAT)) {
                newLine = newLine.trim();
                String[] mapParts = newLine.split("\\s*:\\s*");
                if(mapParts.length == 2) {
                    classProperties.put(mapParts[0], Double.parseDouble(mapParts[1]));
                }
            }
        }
    }

    public static Number getFromDictionary(String value){
        if (classProperties.get(value) == null) {
            System.err.println("value not found");
        }
        return classProperties.get(value);
    }
}