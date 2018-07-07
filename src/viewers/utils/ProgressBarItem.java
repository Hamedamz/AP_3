package viewers.utils;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProgressBarItem extends Pane {
    private Label label = new Label();
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

        this.getChildren().add(new VBox(this.label, new HBox(Const.SPACING, this.icon, progressBar)));
        setStyle();
    }

    private void setStyle() {
        progressBar.setId(type.getId());
        switch (type) {
            case INFO:
                break;
            case ELIXIR_INFO:
                break;
            case GOLD_INFO:
                break;
            case HIT_POINTS:
                progressBar.setMaxWidth(Const.SMALL_PROGRESS_BAR_WIDTH);
                setIconVisibility(false);
                setLabelVisibility(false);
                break;
            case REMAINED_TIME:
                progressBar.setMaxWidth(Const.SMALL_PROGRESS_BAR_WIDTH);
                setIconVisibility(false);
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
            label.setText(title + ": " + value + "/" + max);
        } else if (type.equals(ProgressBarType.REMAINED_TIME)){
            label.setText(max - value + "s");
        } else {
            label.setText(value + "/" + max);
        }
    }

    public void setLabelVisibility(boolean visibility) {
        label.setVisible(visibility);
    }

    public void setIconVisibility(boolean visibility) {
        icon.setVisible(visibility);
    }


}
