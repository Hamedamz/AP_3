package controllers.multiPlayer.runnables;

import controllers.multiPlayer.packet.serverPacket.ServerPacket;

public interface ServerPacketListener<T extends ServerPacket> {
    void receive(T t);
}
