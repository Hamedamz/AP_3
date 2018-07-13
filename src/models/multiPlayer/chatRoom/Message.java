package models.multiPlayer.chatRoom;

import java.io.Serializable;

public class Message implements Serializable {
    private String text;
    private String from;

    public Message(String text, String from, String to) {
        this.text = text;
        this.from = from;
    }

    @Override
    public String toString() {
        return from + " : " + text;
    }

    public String getText() {
        return text;
    }

    public String getFrom() {
        return from;
    }
}
