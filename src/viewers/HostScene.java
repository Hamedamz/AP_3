package viewers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import viewers.utils.Const;
import viewers.utils.SliderMenu.SliderMenu;
import viewers.utils.riverMenu.HostMenu;

import static viewers.utils.Const.WINDOW_HEIGHT;
import static viewers.utils.Const.WINDOW_WIDTH;

public class HostScene extends Scene {

    private static HostScene instance = new HostScene();
    public static HostScene getInstance() {
        return instance;
    }

    private StackPane container;
    private Group root;

    public HostScene() {
        super(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        root = (Group) getRoot();
        this.getStylesheets().add("/viewers/styles/game.css");
        build();
    }

    private void build() {
        container = new StackPane(HostMenu.getInstance());
        container.setPrefSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);

        root.getChildren().clear();
        root.getChildren().addAll(container, SliderMenu.getInstance());
    }
}
