package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.Packet;

public class ServerPacket extends Packet {
    private ServerPacketType packetType;

    public ServerPacketType getPacketType() {
        return packetType;
    }

    public ServerPacket withPacketType(ServerPacketType packetType) {
        this.packetType = packetType;
        return this;
    }
}
