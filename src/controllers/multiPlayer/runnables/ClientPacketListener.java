package controllers.multiPlayer.runnables;



import controllers.multiPlayer.packet.Packet;
import controllers.multiPlayer.packet.clientPacket.ClientPacket;

/**
 * Created by mahdihs76 on 5/21/18.
 */
public interface ClientPacketListener<T extends ClientPacket> {
    void receive(T t);
}
