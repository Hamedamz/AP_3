package models.multiPlayer.packet.clientPacket;

import models.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientBroadcastPacket extends ClientPacket {
    protected ClientBroadcastPacket(ClientPacketType packetType, Object... elements) {
        super(packetType, elements);
    }
}
