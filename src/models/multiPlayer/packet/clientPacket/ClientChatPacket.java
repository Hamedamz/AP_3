package models.multiPlayer.packet.clientPacket;

public class ClientChatPacket extends ClientPacket {
    private ClientChatPacketType clientChatPacketType;

    public ClientChatPacket(ClientChatPacketType clientChatPacketType, Object... elements) {
        super(ClientPacketType.CHAT_ROOM, elements);
        this.clientChatPacketType = clientChatPacketType;
    }

    public ClientChatPacketType getClientChatPacketType() {
        return clientChatPacketType;
    }
}
