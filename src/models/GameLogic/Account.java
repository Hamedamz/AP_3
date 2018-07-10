package models.GameLogic;

import java.io.File;
import java.util.HashMap;

public class Account {
    private HashMap<File, GameMap> enemyVillagesFileAndMap;
    private Village myVillage;

    public Account() {
        enemyVillagesFileAndMap = new HashMap<>();
        myVillage = Village.startNewVillage();
    }

    public HashMap<File, GameMap> getEnemyVillagesFileAndMap() {
        return enemyVillagesFileAndMap;
    }

    public void setEnemyVillagesFileAndMap(HashMap<File, GameMap> enemyVillagesFileAndMap) {
        this.enemyVillagesFileAndMap = enemyVillagesFileAndMap;
    }

    public Village getMyVillage() {
        return myVillage;
    }

    public void setMyVillage(Village myVillage) {
        this.myVillage = myVillage;
    }
}
