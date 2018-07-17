package controllers.multiPlayer.packet.clientPacket;

import controllers.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientBroadcastPacket extends ClientPacket {
    protected ClientBroadcastPacket(ClientPacketType packetType, Object... elements) {
        super(packetType, elements);
    }
}
