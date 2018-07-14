package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.chatRoom.Message;

public class ServerChatPacket extends ServerPacket {
    private ServerChatPacketType chatPacketType;

    public ServerChatPacket(ServerChatPacketType chatPacketType, Object... elements) {
        super(ServerPacketType.CHAT_ROOM, elements);
        this.chatPacketType = chatPacketType;
    }

    public ServerChatPacketType getChatPacketType() {
        return chatPacketType;
    }

    public Message getMessage() {
        return (Message) getElements()[0];
    }

    public ServerChatPacket withChatPacketType(ServerChatPacketType packetType) {
        chatPacketType = packetType;
        return this;
    }



}
