package models;

import models.Exceptions.FileNotFound;

public class World {
    private Village myVillage;
    private Village underAttackVillage;
    private BattleGround battleGround;
    private int turn;

    public World() {
        myVillage = new Village();
        turn = 0;
    }

    public Village getMyVillage() {
        return myVillage;
    }

    public Village getUnderAttackVillage() {
        return underAttackVillage;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public int getTurn() {
        return turn;
    }

    private void convertJsonToVillage(String[] jsonThings){

    }

    public void makeNewGame() {

    }
    public void saveGame() {

    }

    private boolean hasBattleEnded() {

    }


    public void loadMyVillage(String path) throws FileNotFound {

    }

    public void loadEnemyVillage(String path) throws FileNotFound {

    }


    public boolean attackVillage(String path) throws FileNotFound {
        //maybe this changes
    }

    public void passTurn() {

    }
}
