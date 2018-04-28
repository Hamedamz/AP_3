package models.GameLogic.Entities.Buildings;

import models.GameLogic.Resource;

public abstract class Mine extends Building{
    protected int productionRate;

    public abstract Resource produce();
}
