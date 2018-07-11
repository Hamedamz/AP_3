package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.chatRoom.Message;

public class ChatServerPacket extends ServerPacket {
    private ChatServerPacketType chatPacketType;
    private Message message;

    public ChatServerPacketType getChatPacketType() {
        return chatPacketType;
    }

    public Message getMessage() {
        return message;
    }

    public ChatServerPacket withChatPacketType(ChatServerPacketType packetType) {
        chatPacketType = packetType;
        return this;
    }

    public ChatServerPacket withMessage(Message message) {
        this.message = message;
        return this;
    }


}
