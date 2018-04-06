package models;

import models.enums.LoadVillageReturnValue;

import java.util.ArrayList;

public class World {
    private Village myVillage;
    private Village underAttackVillage;
    private BattleGround battleGround;
    private int turn;

    public World() {
        myVillage = new Village();
        turn = 0;
    }

    private void convertJsonToVillage(String[] jsonThings){

    }


    public LoadVillageReturnValue loadMyVillage(String path) {

    }

    public LoadVillageReturnValue loadEnemyVillage() {

    }

    public boolean attackVillage(String path) {
        //maybe this changes
    }


}
