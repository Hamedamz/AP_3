package controllers.multiPlayer.packet.clientPacket;

import controllers.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientConnectionPacket extends ClientPacket{
    public ClientConnectionPacket(Object... elements) {
        super(ClientPacketType.CONNECTION, elements);
    }
}
