package viewers.utils;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProgressBarItem extends Pane {
    private Label label;
    private ImageView icon;
    private ProgressBar progressBar;
    private String title;
    private double value;
    private double max;

    public ProgressBarItem(String title, Image icon, double value, double max) {
        this.title = title;
        this.icon = new ImageView(icon);
        this.value = value;
        this.max = max;
        this.label = new Label(getInfo());
        this.progressBar = new ProgressBar();
        setProgressValue(value);

        this.getChildren().add(new VBox(Const.SPACING, this.label, new HBox(Const.SPACING, this.icon, progressBar)));
    }

    public void setProgressValue(double value) {
        progressBar.setProgress(value / max);
    }

    private String getInfo() {
        return title + ": " + value + "/" + max;
    }
}
