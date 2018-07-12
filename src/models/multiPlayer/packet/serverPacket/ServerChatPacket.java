package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.chatRoom.Message;

public class ServerChatPacket extends ServerPacket {
    private ServerChatPacketType chatPacketType;
    private Message message;

    public ServerChatPacket(ServerChatPacketType chatPacketType, Object... elements) {
        super(ServerPacketType.CHAT_ROOM, elements);
        this.chatPacketType = chatPacketType;
    }

    public ServerChatPacketType getChatPacketType() {
        return chatPacketType;
    }

    public Message getMessage() {
        return message;
    }

    public ServerChatPacket withChatPacketType(ServerChatPacketType packetType) {
        chatPacketType = packetType;
        return this;
    }

    public ServerChatPacket withMessage(Message message) {
        this.message = message;
        return this;
    }


}
