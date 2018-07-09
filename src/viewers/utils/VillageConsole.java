package viewers.utils;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import models.GameLogic.Village;
import models.GameLogic.VillageCheater;
import viewers.AppGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class VillageConsole extends BorderPane {
    protected final TextArea textArea = new TextArea();
    protected final TextField textField = new TextField();

    protected final List<String> history = new ArrayList<>();
    protected int historyPointer = 0;

    private Consumer<String> onMessageReceivedHandler;

    private Village village;
    private boolean isMinimized = true;

    public VillageConsole() {
        setStyles();
        textArea.setEditable(false);
        setCenter(textArea);
        minimize();

        setOnMessageReceivedHandler();


        textField.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    String text = textField.getText();
                    if(text.equals("")) break;
                    textArea.appendText(text + System.lineSeparator());
                    if (history.size() == 0 || !history.get(history.size() - 1).equals(text)) {
                        history.add(text);
                        historyPointer = history.size();
                    }
                    if (onMessageReceivedHandler != null) {
                        onMessageReceivedHandler.accept(text);
                    }
                    textField.clear();
                    break;
                case UP:
                    if (historyPointer > 0) {
                        historyPointer--;
                    }
                    textField.setText(history.get(historyPointer));
                    textField.end();
                    break;
                case DOWN:
                    if (historyPointer < history.size() - 1) {
                        historyPointer++;
                    } else {
                        historyPointer = history.size() - 1;
                    }
                    textField.setText(history.get(historyPointer));
                    textField.end();
                    break;
                case BACK_QUOTE:
                    textField.setText(textField.getText().replaceAll("`", ""));
                    break;
            }
        });
        setBottom(textField);

        textField.requestFocus();
    }

    private void setStyles() {
        getStylesheets().addAll("viewers/styles/console.css");
        textArea.setId("history-textarea");
        textField.setId("history-textfield");
        this.setId("village-console");
        this.setOpacity(0.7);
        this.setPrefWidth(Const.WINDOW_WIDTH);
        this.setPrefHeight(Const.WINDOW_HEIGHT/6);
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        textField.requestFocus();
    }

    public void setOnMessageReceivedHandler() {
        onMessageReceivedHandler = s -> {
            if(village != null) {
                VillageCheater.cheatInVillage(village, s);
            }
        };
    }

    public void clear() {
        textArea.clear();
    }

    public void print(final String text) {
        Objects.requireNonNull(text, "text");
        textArea.appendText(text);
    }

    public void println(final String text) {
        Objects.requireNonNull(text, "text");
        textArea.appendText(text + System.lineSeparator());
    }

    public void println() {
        textArea.appendText(System.lineSeparator());
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public void minimize(){
        setDisable(true);
        setVisible(false);
        isMinimized = true;
    }

    public void maximize(){
        setDisable(false);
        setVisible(true);
        setVillage(AppGUI.getController().getSinglePlayerWorld().getMyVillage());
        isMinimized = false;
        textField.requestFocus();
    }

    public boolean isMinimized() {
        return isMinimized;
    }

}
