package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.serverPacket.types.ServerInteractionPacketType;
import models.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerInteractionPacket extends ServerPacket {
    private ServerInteractionPacketType battleManagerPacketType;
    private boolean isRequest;

    public ServerInteractionPacket(ServerInteractionPacketType battleManagerPacketType, boolean isRequest, Object... elements) {
        super(ServerPacketType.BATTLE_MANAGER, elements);
        this.battleManagerPacketType = battleManagerPacketType;
        this.isRequest = isRequest;
    }

    public ServerInteractionPacketType getBattleManagerPacketType() {
        return battleManagerPacketType;
    }

    public boolean isReceiveRequest(){
        return isRequest;
    }
}
