package viewers.utils;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import viewers.utils.fancyButtons.EntityFancyButton;

public class ShopScrollMenu extends ScrollMenu {
    public ShopScrollMenu(String[] entities) {
        super(entities);
        build();
    }

    @Override
    void build() {
        for (String clazz : getEntities()) {
            EntityFancyButton entityFancyButton = new EntityFancyButton(ButtonActionType.BUILD_BUILDING, clazz);
            getButtons().getChildren().add(entityFancyButton);

            entityFancyButton.setOnDragDetected(event -> {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = entityFancyButton.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(entityFancyButton.getClazz());
                db.setContent(content);

                event.consume();
            });
        }
    }
}