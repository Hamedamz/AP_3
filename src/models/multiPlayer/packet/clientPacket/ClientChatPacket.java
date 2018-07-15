package models.multiPlayer.packet.clientPacket;

import models.multiPlayer.packet.clientPacket.types.ClientChatPacketType;
import models.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientChatPacket extends ClientPacket {
    private ClientChatPacketType chatPacketType;

    public ClientChatPacket(ClientChatPacketType chatPacketType, Object... elements) {
        super(ClientPacketType.CHAT_ROOM, elements);
        this.chatPacketType = chatPacketType;
    }

    public ClientChatPacketType getChatPacketType() {
        return chatPacketType;
    }
}
