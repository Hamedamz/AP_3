package models.Setting;

import javax.imageio.IIOException;
import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class ConstantClassProperties {
    private static Map<String, Number> classProperties;

    private static final String CONSTANTS_SAVE_LOCATION = "class properties.txt";
    private static final String IGNORE_REGEX = "#.*";
    private static final String PROPERTY_FORMAT = "((\\D(_\\d+)?)|\\s)+:\\s+\\d+(\\.\\d+)?";

    public static void loadConstants() {
        classProperties = new HashMap<>();
        try {
            File constantFile = new File(CONSTANTS_SAVE_LOCATION);
            InputStream stream = new FileInputStream(constantFile);
            Scanner scanner = new Scanner(stream);
            while (scanner.hasNext()) {
                String newLine = scanner.nextLine();
                if (!newLine.matches(IGNORE_REGEX)) {
                    newLine = newLine.replaceAll("\\s+", " ");
                    if(newLine.matches(PROPERTY_FORMAT)) {
                        if(newLine.charAt(0) == ' ') {
                            newLine = newLine.substring(1);
                        }
                        String[] mapParts = newLine.split("\\s*:\\s*");
                        if(mapParts.length == 2) {
                            classProperties.put(mapParts[0], Double.parseDouble(mapParts[1]));
                        }
                    }
                }
            }
            String key = scanner.next();
            int value = scanner.nextInt();
            classProperties.put(key, value);
        } catch (FileNotFoundException e) {
            System.err.println("constant file not found");
        }
    }

    public static Number getFromDictionary(String value) {
        return classProperties.get(value);
    }
}
