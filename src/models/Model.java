package models;

import java.util.ArrayList;

public class Model {
    private Village myVillage;
    private Map underAttackMap;
    private BattleGround battleGround;
    private int deltaT;

    public Model() {
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
