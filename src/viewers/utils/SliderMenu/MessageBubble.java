package viewers.utils.SliderMenu;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.multiPlayer.chatRoom.Message;

public class MessageBubble extends GridPane {
    private Message message;
    private Text from;
    private Text text;

    public MessageBubble(Message message) {
        this.message = message;
        from = new Text(message.getFrom());
        from.setFill(Color.AQUA);
        text = new Text(message.getText());
        text.setFill(Color.WHITE);
        this.add(from, 0,0);
        this.add(text, 0,1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            return this.message.equals(obj);
        }
        if (obj instanceof MessageBubble){
            return this.message.equals(((MessageBubble) obj).message);
        }
        return super.equals(obj);
    }
}
