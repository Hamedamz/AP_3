package models.multiPlayer.runnables;



import models.multiPlayer.packet.Packet;

/**
 * Created by mahdihs76 on 5/21/18.
 */
public interface PacketListener<T extends Packet> {
    void receive(T t);
}
