package models.Setting;

import javax.imageio.IIOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class ConstantClassProperties {
    private static Map<String, Integer> classProperties;

    public static final String CONSTANTS_SAVE_LOCATION = "class properties.txt";

    public static void loadConstants() {
        classProperties = new HashMap<>();
        try {
            File constantFile = new File(CONSTANTS_SAVE_LOCATION);
            InputStream stream = new FileInputStream(constantFile);
            Scanner scanner = new Scanner(stream);
            String key = scanner.next();
            int value = scanner.nextInt();
            classProperties.put(key, value);
        } catch (FileNotFoundException e) {
            System.err.println("constant file not found");
        }
    }

    public static int getFromDictionary(String value) {
        return classProperties.get(value);
    }
}
