package models.multiPlayer.packet.clientPacket;

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
