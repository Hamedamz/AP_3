package viewers.utils.SliderMenu;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.multiPlayer.chatRoom.Message;

public class MessageBubble extends GridPane {
    private Text from;
    private Text text;

    public MessageBubble(Message message) {
        from = new Text(message.getFrom());
        from.setFill(Color.AQUA);
        text = new Text(message.getText());
        text.setFill(Color.WHITE);
        this.add(from, 0,0);
        this.add(text, 0,1);
    }

    public MessageBubble() {

    }
}
