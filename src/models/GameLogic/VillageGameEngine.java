package models.GameLogic;

import interfaces.Revivable;
import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Mine;

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
        // FIXME: 4/26/2018 add village Gaint to revivables

        updateTrainingTroops();

        for (Builder builder : village.getTownHall().getBuilders()) {
            builder.build();
        }

        Resource addedResource = new Resource(0, 0);
        for(Building building : village.findBuildingsWithSameType("ElixirMine")) {
            addedResource.addToThisResource(((Mine) building).produce());
        }

        for(Building building : village.findBuildingsWithSameType("GoldMine")) {
            addedResource.addToThisResource(((Mine) building).produce());
        }

        village.addResources(addedResource);

    }

    public void updateTrainingTroops() {
        for(Building building : village.findBuildingsWithSameType("Barracks")) {
            for(TrainingTroop trainingTroop : ((Barracks) building).getTrainingTroops()) {
                trainingTroop.update();
                if (trainingTroop.hasEndedTraining()) {
                    trainingTroop.moveToCamp(village.findCampForNewTroops(trainingTroop.getSpace()));
                }
            }
        }
    }

}
