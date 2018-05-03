package models.GameLogic.Entities.Buildings;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Map;
import models.GameLogic.Position;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.Setting.GameLogicConfig;

public abstract class DefensiveBuilding extends Building implements Attacker {
    protected int damage;
    protected int range;
    protected BuildingDamageType damageType;
    protected BuildingTargetType targetType;
    protected Destroyable target;

    public DefensiveBuilding(Position position, int number) {
        super(position, number);
        String className = this.getClass().getName();
        this.damage = (Integer) GameLogicConfig.getFromDictionary(className + "Damage");
        this.range = (Integer) GameLogicConfig.getFromDictionary(className + "Range");
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

    }

    @Override
    public void giveDamageTo(Destroyable destroyable, Map map) {
        if (damageType == BuildingDamageType.AREA_SPLASH) {
            destroyable.takeDamageFromAttack(damage);
            if (destroyable.isDestroyed()) {
                destroyable.destroy();
            }
        }
        else {
            for (Entity entity : map.getTroops(destroyable.getPosition())) {
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
    public Destroyable setTarget(BattleGround battleGround) {
        // TODO: 4/19/2018 complete this after battleGround
    }
}
