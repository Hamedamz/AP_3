package models.multiPlayer;

import models.multiPlayer.packet.clientPacket.ClientPacket;
import models.multiPlayer.runnables.PacketListener;

public class Client extends PacketHandler implements PacketListener<ClientPacket> {

    @Override
    public void receive(ClientPacket clientPacket) {
        switch (clientPacket.getPacketType()) {
            // TODO: 7/11/2018  
        }
    }
}
