package viewers.utils.entityHolders;

import javafx.animation.ScaleTransition;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Healer;

public class TroopHolder extends EntityHolder{
    private ScaleTransition scaleTransition = new ScaleTransition();

    public TroopHolder(Entity entity) {
        super(entity);
        initialize();
    }

    @Override
    public void initialize() {
        super.initialize();

    }

    @Override
    public void refresh() {
        if (entity.getClass().equals(Healer.class)) {
            return;
        }
        if (((AttackerTroop) entity).getHitPoints() < ((AttackerTroop) entity).getMaxHitPoints()) {
            if (!hitPointsProgressBar.isVisible()) {
                hitPointsProgressBar.setVisible(true);
            }
            hitPointsProgressBar.setValues();
        } else if (hitPointsProgressBar.isVisible()){
            hitPointsProgressBar.setVisible(false);
        }
    }

    @Override
    public boolean isDestroyed() {
        if (entity instanceof Healer) {
            return ((Healer) entity).isDestroyed();
        }
        return ((AttackerTroop) entity).isDestroyed();
    }
}
