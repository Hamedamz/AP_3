package models.multiPlayer.packet.clientPacket;

import models.multiPlayer.packet.clientPacket.types.ClientInteractionPacketType;
import models.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientInteractionPacket extends ClientPacket {
    private ClientInteractionPacketType battleManagerPacketType;
    private boolean isRequest;

    public ClientInteractionPacket(ClientInteractionPacketType battleManagerPacketType, boolean isRequest, Object... elements) {
        super(ClientPacketType.BATTLE_MANAGER, elements);
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
