package models.multiPlayer.runnables;

import models.multiPlayer.packet.serverPacket.ServerPacket;

public interface ServerPacketListener<T extends ServerPacket> {
    void receive(T t);
}
