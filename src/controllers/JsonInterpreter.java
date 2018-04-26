package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.GameLogic.Village;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JsonInterpreter {
    private static final String SAVED_MAPS_FOLDER_NAME = "savedMaps";
    private static Gson gson = new GsonBuilder().create();

    public static void saveVillage(Village village, String villageName) {
        try {
            createFolder(SAVED_MAPS_FOLDER_NAME);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String filePath = SAVED_MAPS_FOLDER_NAME + "\\" + villageName + ".json";
        try (Writer writer = new FileWriter(filePath)){
            gson.toJson(village, writer);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createFolder(String folderName) throws Exception{
        File savePath = new File(folderName);
        if (savePath.exists()) {
            return;
        }
        savePath.mkdirs();
    }
}
