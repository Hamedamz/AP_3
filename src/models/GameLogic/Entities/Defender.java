package models.GameLogic.Entities;

import interfaces.Revivable;
import interfaces.Updatable;
import models.GameLogic.Position;

public abstract class Defender extends Entity implements Revivable, Updatable {
    public Defender(Position position){
        super(position);
    }

    public Defender() {
    }
}
