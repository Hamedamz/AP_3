package models.GameLogic.Entities.Buildings;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Map;
import models.GameLogic.Position;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.ID;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class DefensiveBuilding extends Building implements Attacker {
    protected int damage;
    protected int range;
    protected BuildingDamageType damageType;
    protected BuildingTargetType targetType;
    protected Destroyable target;

    public DefensiveBuilding(Position position, ID id) {
        super(position, id);
        String className = this.getClass().getName();
        this.damage = (int) GameLogicConfig.getFromDictionary(className + "Damage");
        this.range = (int) GameLogicConfig.getFromDictionary(className + "Range");
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public BuildingDamageType getDamageType() {
        return damageType;
    }

    public BuildingTargetType getTargetType() {
        return targetType;
    }

    public boolean canTowerAttack(Entity Troop) {
        return true;
        // TODO: 5/5/2018
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        if (damageType == BuildingDamageType.SINGLE_TARGET) {
            destroyable.takeDamageFromAttack(damage);
            if (destroyable.isDestroyed()) {
                destroyable.destroy();
            }
        }
        else {
            for (Entity entity : battleGround.getAttackerEntitiesInPosition(destroyable.getPosition())) {
                if(entity instanceof Destroyable) {
                    ((Destroyable) entity).takeDamageFromAttack(damage);
                    if (destroyable.isDestroyed()) {
                        destroyable.destroy();
                    }
                }
            }
        }

    }

    @Override
    public Destroyable getTarget() {
        return target;
    }


    @Override
    public void setTarget(ArrayList<Destroyable> destroyables) {
//        int x = building.getPosition().getX();
//        int y = building.getPosition().getY();
//        if (building instanceof DefensiveBuilding) {
//            for (int i = -((DefensiveBuilding) building).getRange(); i <((DefensiveBuilding) building).getRange(); i++) {
//                x += i;
//                for (int j = -((DefensiveBuilding) building).getRange(); j <= ((DefensiveBuilding) building).getRange(); j++) {
//                    y += j;
//                    if (x >= 30)
//                        x = 29;
//                    if (y >= 30)
//                        y = 29;
//                    if (x < 0)
//                        x = 0;
//                    if (y < 0)
//                        y = 0;
//                    if (building.getPosition().calculateDistance(new Position(x, y)) > ((DefensiveBuilding) building).getRange()) {
//                        continue;
//                    }
//                    for (Iterator<Troop> it = battleGround.getTroops().iterator(); it.hasNext(); ) {
//                        Troop troop = it.next();
//                        if (troop.getPosition().getX() == x && troop.getPosition().getY() == y)
//                            return troop;
//                    }
//                }
//            }
//        }
        return;
    }

    @Override
    public int getEffectRange() {
        return getRange();
    }
}
