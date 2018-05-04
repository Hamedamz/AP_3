package models.GameLogic.Entities;

import interfaces.Revivable;
import models.GameLogic.Position;

public abstract class Defender extends Entity implements Revivable {
    public Defender(Position position){
        super(position);
    }
}
