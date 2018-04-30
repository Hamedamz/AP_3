package models.GameLogic;

import interfaces.Revivable;
import models.GameLogic.Entities.Buildings.*;

public class VillageGameEngine {
    private Village village;

    public VillageGameEngine(Village village) {
        this.village = village;
    }

    public void loadVillage(Village village) {
        this.village = village;
    }

    public void update() {
        for(Revivable revivable : village.getBuildings()) {
            if (revivable.isDestroyed()) {
                revivable.revive();
            }
        }
        // FIXME: 4/26/2018 add village Giant to revivables

        updateTrainingTroops();

        for (Builder builder : village.getTownHall().getBuilders()) {
            builder.build();
        }

        Resource addedResource = new Resource(0, 0);
        for(Building building : village.findBuildingsWithSameType(ElixirMine.class)) {
            addedResource.addToThisResource(((Mine) building).produce());
        }

        for(Building building : village.findBuildingsWithSameType(GoldMine.class)) {
            addedResource.addToThisResource(((Mine) building).produce());
        }

        village.spreadResources(addedResource);

    }

    public void updateTrainingTroops() {
        for(Building building : village.findBuildingsWithSameType(Barracks.class)) {
            for(TrainingTroop trainingTroop : ((Barracks) building).getTrainingTroops()) {
                trainingTroop.update();
                if (trainingTroop.hasEndedTraining()) {
                    trainingTroop.moveToCamp(village.findCampForNewTroops(trainingTroop.getSpace()));
                }
            }
        }
    }

}
