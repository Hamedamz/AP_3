package models.GameLogic.Entities.Buildings;

import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.Setting.GameLogicConfig;

public abstract class Mine extends ResourceBuilding{
    protected int productionRate;

    public Mine(Position position, int number) {
        super(position, number);
        String className = this.getClass().getName();
        this.productionRate = (Integer) GameLogicConfig.getFromDictionary(className + "ProductionRate");
    }

    public abstract Resource produce();
}
