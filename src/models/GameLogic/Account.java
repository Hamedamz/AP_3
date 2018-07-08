package models.GameLogic;

import java.util.HashMap;

public class Account {
    private HashMap<String, GameMap> enemyVillagesPathAndMap;
    private Village myVillage;

    public Account() {
        enemyVillagesPathAndMap = new HashMap<>();
        myVillage = Village.startNewVillage();
    }

    public HashMap<String, GameMap> getEnemyVillagesPathAndMap() {
        return enemyVillagesPathAndMap;
    }

    public void setEnemyVillagesPathAndMap(HashMap<String, GameMap> enemyVillagesPathAndMap) {
        this.enemyVillagesPathAndMap = enemyVillagesPathAndMap;
    }

    public Village getMyVillage() {
        return myVillage;
    }

    public void setMyVillage(Village myVillage) {
        this.myVillage = myVillage;
    }
}
