package models;

import java.util.HashMap;
import java.util.Map;

public abstract class Dictionary {
    private static Map<String ,Object> resourceDictionary;

    public static boolean loadDictionary(String path) {
        resourceDictionary = new HashMap<>();
        //loads dictionary from path
    }

    public static Object getFromDictionary(String string) {

    }
}
