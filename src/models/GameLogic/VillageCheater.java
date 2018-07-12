package models.GameLogic;

import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;

public class VillageCheater {
    public static final String FULL_MONEY = "-banker";
    public static final String FULL_ELIXIR = "-alchemist";
    public static final String FULL_RESOURCES = "-goodgame";
    public static final String END_TRAINING = "-strategist";
    public static final String END_BUILDING = "-architect";
    public static final String STRONG_SOLDIERS = "-hercules";
    public static final String RESET_CHEATS = "-reset";

    private static Thread resourceSpreader;
    private static Thread constructionEnder;

    /**
     * performs cheats on village
     * -banker for full gold
     * -alchemist for full elixir
     * -goodgame for both gold and elixir
     * -strategist for finishing all trainings
     * -architect for finishing all underConstruct buildings
     * -hercules for invulnerable attacker troops
     * -reset for removing -hercules effect
     * @param village targeted Village
     * @param cheatCode cheat code in above formats
     */
    public static void cheatInVillage(Village village, String cheatCode){
        if (cheatCode.contains(FULL_RESOURCES)) {
            makeResourceSpreader(village);
        } else if (cheatCode.contains(FULL_MONEY)) {
            village.spreadResources(new Resource(Integer.MAX_VALUE / 2, 0));
        } else if (cheatCode.contains(FULL_ELIXIR)) {
            village.spreadResources(new Resource(0, Integer.MAX_VALUE / 2));
        } else if (cheatCode.contains(END_TRAINING)) {
            for (Building building : village.findBuildingsWithSameType(Barracks.class)) {
                Barracks barracks = (Barracks) building;
                for (TrainingTroop trainingTroop : barracks.getTrainingTroops()) {
                    trainingTroop.finishTraining();
                }
            }
        } else if(cheatCode.contains(END_BUILDING)) {
            makeconstructionEnder(village);
        } else if(cheatCode.contains(STRONG_SOLDIERS)) {
            for(Building building : village.findBuildingsWithSameType(Camp.class)) {
                for(Troop troop : ((Camp) building).getTroops()) {
                    if(troop instanceof AttackerTroop) {
                        ((AttackerTroop) troop).setInvulnerable(true);
                    }
                }
            }
        }  else if(cheatCode.contains(RESET_CHEATS)) {
            for(Building building : village.findBuildingsWithSameType(Camp.class)) {
                for(Troop troop : ((Camp) building).getTroops()) {
                    if(troop instanceof AttackerTroop) {
                        ((AttackerTroop) troop).setInvulnerable(false);
                        if(resourceSpreader != null) {
                            resourceSpreader.interrupt();
                            resourceSpreader = null;
                        }
                        if(constructionEnder != null) {
                            constructionEnder.interrupt();
                            constructionEnder = null;
                        }
                    }
                }
            }
        }
    }

    private static void makeResourceSpreader(Village village) {
        resourceSpreader = new Thread(() -> {
            while (true) {
                village.spreadResources(new Resource(Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        resourceSpreader.setDaemon(true);
        resourceSpreader.start();
    }

    private static void makeconstructionEnder(Village village) {
        constructionEnder = new Thread(() -> {
            while (true) {
                for (Builder builder : village.getTownHall().getBuilders()) {
                    builder.finishBuilding();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        constructionEnder.setDaemon(true);
        constructionEnder.start();
    }
}
