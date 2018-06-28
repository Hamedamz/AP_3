package controllers;

import java.util.ArrayList;

public class JsonVillage {
    int[] size = new int[2];
    ArrayList<JsonWall> walls = new ArrayList<>();
    JsonResources resources = new JsonResources();
    ArrayList<JsonBuilding> buildings = new ArrayList<>();
}

class JsonWall {
    int level;
    int x;
    int y;
}

class JsonResources {
    int gold;
    int elixir;
}

class JsonBuilding {
    int type;
    int level;
    int x;
    int y;
    int amount;
}


