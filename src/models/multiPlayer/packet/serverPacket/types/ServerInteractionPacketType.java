package models.multiPlayer.packet.serverPacket.types;

/**
 * elements order:
 * { battleManagerPacketType: VIEW_S, elements: {isRequest: true, requesterID} }
 * { battleManagerPacketType: VIEW_S, elements: {isRequest: false, requesterID, villageJson} }
 */
public enum ServerInteractionPacketType {
    VIEW_S,
    ATTACK_S,
    END_ATTACK_S;
}
