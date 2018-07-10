package viewers.utils;

import javafx.geometry.Insets;
import javafx.scene.Node;
import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Exceptions.NotAvailableAtThisLevelException;
import models.GameLogic.Exceptions.NotEnoughResourcesException;
import models.GameLogic.Resource;
import models.Setting.GameLogicConfig;
import viewers.AppGUI;
import viewers.utils.fancyButtons.ButtonActionType;
import viewers.utils.fancyButtons.TroopsFancyButton;

public class TrainTroopsScrollMenu extends ScrollMenu {

    Object model;

    public TrainTroopsScrollMenu(String[] entities, Object model) {
        super(entities);
        this.model = model;
        build();
    }

    @Override
    void build() {
        for (String clazz : getEntities()) {
            TroopsFancyButton troopsFancyButton = new TroopsFancyButton(ButtonActionType.NONE, clazz);
            getButtons().getChildren().add(troopsFancyButton);

            troopsFancyButton.setOnMouseClicked(event -> {
                try {
                    AppGUI.getController().trainTroop(clazz, 1, (Barracks) model);
                } catch (NotEnoughResourcesException | NotAvailableAtThisLevelException e) {
                    AppGUI.getVillageScene().handleException(e);
                }
            });
        }
    }

    public void refresh() {
        Resource resourceStock = AppGUI.getController().getSinglePlayerWorld().getMyVillage().getTotalResourceStock();
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
}
