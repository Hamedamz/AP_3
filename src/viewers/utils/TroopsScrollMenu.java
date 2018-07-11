package viewers.utils;

import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Exceptions.NotAvailableAtThisLevelException;
import models.GameLogic.Exceptions.NotEnoughResourcesException;
import models.GameLogic.Resource;
import models.setting.GameLogicConfig;
import viewers.AppGUI;
import viewers.utils.fancyButtons.ButtonActionType;
import viewers.utils.fancyButtons.TroopsFancyButton;

import java.util.HashMap;

public class TroopsScrollMenu extends ScrollMenu {

    Object model;

    public TroopsScrollMenu(String[] entities, Object model) {
        super(entities);
        this.model = model;
        build();
    }

    private void build() {
        for (String clazz : getEntities()) {
            TroopsFancyButton troopsFancyButton = new TroopsFancyButton(ButtonActionType.NONE, clazz);
            getButtons().getChildren().add(troopsFancyButton);
        }
    }

    public void buildForTraining() {
        for (int i = 0; i < getEntities().length; i++) {
            String troop = getEntities()[i];
            TroopsFancyButton troopsFancyButton = (TroopsFancyButton) getButtons().getChildren().get(i);

            troopsFancyButton.setOnMouseClicked(event -> {
                try {
                    AppGUI.getController().trainTroop(troop, 1, (Barracks) model);
                } catch (NotEnoughResourcesException | NotAvailableAtThisLevelException e) {
                    AppGUI.getMyVillageScene().handleException(e);
                }
            });
        }
    }

    public void buildForAttackMenu() {
        for (int i = 0; i < getEntities().length; i++) {
            TroopsFancyButton troopsFancyButton = (TroopsFancyButton) getButtons().getChildren().get(i);
            troopsFancyButton.hideLabel();
            troopsFancyButton.showButtons();
        }
    }

    public void refreshForTraining() {
        Resource resourceStock = AppGUI.getController().getWorld().getMyVillage().getTotalResourceStock();
        Barracks barracks = (Barracks) model;

        for (int i = 0; i < getEntities().length; i++) {
            String troop = getEntities()[i];
            TroopsFancyButton troopsFancyButton = (TroopsFancyButton) getButtons().getChildren().get(i);
            int elixir = GameLogicConfig.getFromDictionary(troop + "TrainElixir");

            troopsFancyButton.setNumberBadge(resourceStock.getElixir() / elixir);

            if (barracks.getLevel() < 2 && troop.equals("Dragon") || barracks.isUnderConstruct()) {
                troopsFancyButton.setNumberBadge(0);
            }

        }
    }

    public void refreshForArmyStatus() {
        Camp camp = (Camp) model;
        for (int i = 0; i < getEntities().length; i++) {
            String troop = getEntities()[i];
            TroopsFancyButton troopsFancyButton = (TroopsFancyButton) getButtons().getChildren().get(i);
            int numberOfTroop = camp.getNumberOfTroop(troop);
            troopsFancyButton.setNumberBadge(numberOfTroop);
        }
    }

    public HashMap<String, Integer> refreshForAttackMenu() {
        HashMap<String, Integer> troopNumberHashMap = new HashMap<>();
        for (int i = 0; i < getEntities().length; i++) {
            String troop = getEntities()[i];
            TroopsFancyButton troopsFancyButton = (TroopsFancyButton) getButtons().getChildren().get(i);
            int numberOfTroop = 0;
            for (Camp camp : AppGUI.getController().getWorld().getMyVillage().findBuildingsWithSameType(Camp.class)) {
                numberOfTroop += camp.getNumberOfTroop(troop);
            }
            troopsFancyButton.setNumberBadge(numberOfTroop);
            troopNumberHashMap.put(troop, numberOfTroop);
        }
        return troopNumberHashMap;
    }

    public void refreshForBattleGround() {
        for (int i = 0; i < getEntities().length; i++) {
            String troop = getEntities()[i];
            TroopsFancyButton troopsFancyButton = (TroopsFancyButton) getButtons().getChildren().get(i);
            int numberOfTroop = AppGUI.getController().getWorld().getBattleGround().getUnDeployedTroopsNumberByType(troop);
            troopsFancyButton.setNumberBadge(numberOfTroop);
        }
    }
}
