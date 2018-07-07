package models.GameLogic;

import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VillageCheater {
    public static final String FULL_MONEY = "-banker";
    public static final String FULL_ELIXIR = "-alchemist";
    public static final String FULL_RESOURCES = "-goodgame";
    public static final String END_TRAINING = "-strategist";
    public static final String END_BUILDING = "-architect";
    public static final String STRONG_SOLDIERS = "-hercules";
    public static final String NORMAL_SOLDIERS = "-reset";

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
            village.spreadResources(new Resource(Integer.MAX_VALUE, 0));
        } else if (cheatCode.contains(FULL_MONEY)) {
            village.spreadResources(new Resource(Integer.MAX_VALUE, 0));
        } else if (cheatCode.contains(FULL_ELIXIR)) {
            village.spreadResources(new Resource(0, Integer.MAX_VALUE));
        } else if (cheatCode.contains(END_TRAINING)) {
            for (Building building : village.findBuildingsWithSameType(Barracks.class)) {
                Barracks barracks = (Barracks) building;
                for (TrainingTroop trainingTroop : barracks.getTrainingTroops()) {
                    trainingTroop.finishTraining();
                }
            }
        } else if(cheatCode.contains(END_BUILDING)) {
            for (Builder builder : village.getTownHall().getBuilders()) {
                builder.finishBuilding();
            }
        } else if(cheatCode.contains(STRONG_SOLDIERS)) {
            for(Building building : village.findBuildingsWithSameType(Camp.class)) {
                for(Troop troop : ((Camp) building).getTroops()) {
                    if(troop instanceof AttackerTroop) {
                        ((AttackerTroop) troop).setInvulnerable(true);
                    }
                }
            }
        }  else if(cheatCode.contains(NORMAL_SOLDIERS)) {
            for(Building building : village.findBuildingsWithSameType(Camp.class)) {
                for(Troop troop : ((Camp) building).getTroops()) {
                    if(troop instanceof AttackerTroop) {
                        ((AttackerTroop) troop).setInvulnerable(false);
                    }
                }
            }
        }
    }
}
