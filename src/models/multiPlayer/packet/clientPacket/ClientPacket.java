package models.multiPlayer.packet.clientPacket;

import models.multiPlayer.packet.Packet;
import models.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientPacket extends Packet {
    private ClientPacketType packetType;

    protected ClientPacket(ClientPacketType packetType, Object... elements) {
        super(elements);
        this.packetType = packetType;
    }

    public ClientPacketType getPacketType() {
        return packetType;
    }
}
