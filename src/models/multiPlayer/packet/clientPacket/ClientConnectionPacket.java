package models.multiPlayer.packet.clientPacket;

import models.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientConnectionPacket extends ClientPacket{
    public ClientConnectionPacket(Object... elements) {
        super(ClientPacketType.CONNECTION, elements);
    }
}
