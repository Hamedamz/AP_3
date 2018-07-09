package viewers.utils.fancyPopups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import viewers.utils.Const;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

public abstract class ModelPopup extends Popup {

    Object model;
    Label title = new Label();
    Button closeButton = new RoundButton("X", "red");
    VBox content = new VBox(Const.SPACING * 2);
    Pane wrapper = new Pane(content, closeButton);

    public ModelPopup(Object model) {
        this.model = model;
        this.title.setText(model.getClass().getSimpleName());
        content.getChildren().addAll(title);
        this.getContent().add(wrapper);
        this.setAutoHide(true);

        this.title.setAlignment(Pos.CENTER);
        this.title.setMinWidth(Const.POPUP_WIDTH);
        this.title.setId("popup-title");

        content.setPadding(new Insets(10, 10, 30, 10));
        wrapper.setId("popup-wrapper");

        closeButton.setLayoutY(18);
        closeButton.setLayoutX(18);
        closeButton.setOnAction(event -> {
            this.hide();
            SoundPlayer.play(Sounds.buttonSound);
        });
    }

    public void setBody(Pane body) {
        content.getChildren().addAll(body);
    }
}
