package models.GameLogic;

import models.GameLogic.Exceptions.FileNotFoundException;

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

    private void convertJsonToVillage(String[] jsonThings) {

    }

    public void makeNewGame() {

    }

    public void saveGame() {

    }

    private boolean hasBattleEnded() {
        return true;
        // TODO: 5/5/2018
    }


    public void loadMyVillage(String path) throws FileNotFoundException {

    }

    public void loadEnemyVillage(String path) throws FileNotFoundException {

    }


    public boolean attackVillage(String path) throws FileNotFoundException {
        // TODO: 5/5/2018
        return true;
        //maybe this changes
    }

    public void passTurn() {

    }
}
