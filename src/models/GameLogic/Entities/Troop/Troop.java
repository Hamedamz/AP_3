package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import interfaces.Movable;
import interfaces.Upgradable;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.Setting.GameLogicConfig;

public abstract class Troop extends Entity implements Movable, Upgradable, Destroyable {
    private transient Camp troopCamp;
    protected MoveType moveType;
    protected int speed;
    private Position[] movementTarget;

    public Troop() {
        super();
                String className = this.getClass().getName();
        this.speed = (Integer) GameLogicConfig.getFromDictionary(className + "Speed");
    }

    public void setTroopCamp(Camp troopCamp) {
        this.troopCamp = troopCamp;
    }

    public static Troop castStringToTroopType(String string) {
        switch (string) {
            case "Giant" :
                return new Giant();
            case "Archer" :
                return new Archer();
            case "Dragon":
                return new Dragon();
            case "Guardian" :
                return new Guardian();

        }
        return null;
    }
}


//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}