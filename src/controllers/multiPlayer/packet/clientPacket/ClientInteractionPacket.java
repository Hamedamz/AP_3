package controllers.multiPlayer.packet.clientPacket;

import controllers.multiPlayer.packet.clientPacket.types.ClientInteractionPacketType;
import controllers.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientInteractionPacket extends ClientPacket {
    private ClientInteractionPacketType battleManagerPacketType;
    private boolean isRequest;

    public ClientInteractionPacket(ClientInteractionPacketType battleManagerPacketType, boolean isRequest, Object... elements) {
        super(ClientPacketType.INTERACTION, elements);
        this.battleManagerPacketType = battleManagerPacketType;
        this.isRequest = isRequest;
    }

    public ClientInteractionPacketType getBattleManagerPacketType() {
        return battleManagerPacketType;
    }

    public boolean isReceiveRequest(){
        return isRequest;
    }
}
