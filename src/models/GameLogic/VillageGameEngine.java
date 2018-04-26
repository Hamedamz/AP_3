package models.GameLogic;

import interfaces.Revivable;

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

        for(Building building : village.findBuildingsWithSameType("ElixirMine")) {
            village.addResources(((Mine) building).produce());
        }

        for(Building building : village.findBuildingsWithSameType("GoldMine")) {
            village.addResources(((Mine) building).produce());
        }

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
