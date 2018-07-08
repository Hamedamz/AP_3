package viewers.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ProgressBarItem extends Pane {
    private Text label = new Text();
    private ImageView icon = new ImageView();
    private ProgressBar progressBar = new ProgressBar();
    private ProgressBarType type;
    private String title;
    private double value;
    private double max;

    public ProgressBarItem(double value, double max, ProgressBarType type) {
        this.type = type;
        this.max = max;
        setValue(value);

        this.getChildren().add(new VBox(this.label, progressBar));
        setStyle();
    }

    public ProgressBarItem(String title, Image icon, double value, double max, ProgressBarType type) {
        this.type = type;
        this.title = title;
        this.icon.setImage(icon);
        this.max = max;
        setValue(value);
        this.getChildren().add(new HBox(Const.SPACING, this.icon, new VBox(this.label, progressBar)));
        setStyle();
    }

    private void setStyle() {
        label.setId("progress-bar-label");
        progressBar.setId(type.getId());
        progressBar.setMinWidth(type.getWidth());
        progressBar.setMaxWidth(type.getWidth());
        progressBar.setMinHeight(type.getHeight());
        icon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        icon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);

        switch (type) {
            case INFO:
                break;
            case ELIXIR_INFO:
                break;
            case GOLD_INFO:
                break;
            case HIT_POINTS:
                setIconVisibility(false);
                setLabelVisibility(false);
                break;
            case REMAINED_TIME:
                setIconVisibility(false);
                break;
            case INFO_HIT_POINTS:
                break;
        }
    }

    public void setValue(double value) {
        this.value = value;
        updateLabel();
        updateProgress();
    }

    public void setMax(double max) {
        this.max = max;
    }

    private void updateProgress() {
        progressBar.setProgress(value / max);
    }

    private void updateLabel() {
        if (title != null) {
            label.setText(title + ": " + String.format("%.0f/%.0f", value, max));
        } else if (type.equals(ProgressBarType.REMAINED_TIME)){
            label.setText(String.format("%.0f", max - value) + "s");
        } else {
            label.setText(String.format("%.0f/%.0f", value, max));
        }
    }

    private void setLabelVisibility(boolean visibility) {
        label.setVisible(visibility);
    }

    private void setIconVisibility(boolean visibility) {
        icon.setVisible(visibility);
    }


}
