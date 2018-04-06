package models;

import java.util.ArrayList;

public class World {
    private Village myVillage;
    private Map underAttackMap;
    private BattleGround battleGround;
    private int deltaT;

    public World() {
        myVillage = new Village();
        deltaT = 0;
    }

    private void convertJsonToMap(String[] jsonThings){
    }

    public boolean loadMap(String path) {
        return true;
    }

    public void attackMap(Village village, Map underAttackMap) {
    }


}
