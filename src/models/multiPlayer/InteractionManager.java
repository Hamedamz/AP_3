package models.multiPlayer;

import controllers.JsonHandler;
import controllers.multiPlayer.Client;
import controllers.multiPlayer.Server;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import models.AccountInfo;
import models.GameLogic.Village;
import controllers.multiPlayer.packet.clientPacket.ClientInteractionPacket;
import controllers.multiPlayer.packet.serverPacket.ServerInteractionPacket;
import controllers.multiPlayer.runnables.ClientPacketListener;
import controllers.multiPlayer.runnables.ServerPacketListener;
import models.multiPlayer.battleManger.ServerBattleManger;
import models.multiPlayer.battleManger.WarLog;
import models.multiPlayer.leaderBoard.LeaderBoard;

import static controllers.multiPlayer.packet.clientPacket.types.ClientInteractionPacketType.*;
import static controllers.multiPlayer.packet.serverPacket.types.ServerInteractionPacketType.*;

public class InteractionManager implements ClientPacketListener<ClientInteractionPacket>,
        ServerPacketListener<ServerInteractionPacket> {
    private static InteractionManager ourInstance = new InteractionManager();

    public static InteractionManager getInstance() {
        return ourInstance;
    }

    //client stuff
    private ObjectProperty<Village> requestedVillage = new SimpleObjectProperty<>();
    private AccountInfo requestedAccount;
    private boolean isLocked = false;

    private InteractionManager() {
    }

    public void receive(ServerInteractionPacket serverInteractionPacket) {
        switch (serverInteractionPacket.getBattleManagerPacketType()) {
            case VIEW_S:
                if(serverInteractionPacket.isReceiveRequest()) {
                    Server.getInstance().sendToID(new ClientInteractionPacket(VIEW_C, true, serverInteractionPacket.getID()),
                            (String) serverInteractionPacket.getElements()[0]);
                } else {
                    Server.getInstance().sendToID(new ClientInteractionPacket(VIEW_C, false, serverInteractionPacket.getElements()[1]),
                            (String) serverInteractionPacket.getElements()[0]);
                }
                break;
            case ATTACK_S:
                if(serverInteractionPacket.isReceiveRequest()) {
                    Server.getInstance().sendToID(new ClientInteractionPacket(LOCK, true, serverInteractionPacket.getID()),
                            (String) serverInteractionPacket.getElements()[0]);
                } else {
                    ServerBattleManger.getInstance().makeNewBattle((String) serverInteractionPacket.getElements()[0],
                            serverInteractionPacket.getID());
                    Server.getInstance().sendToID(new ClientInteractionPacket(ATTACK_C, false, serverInteractionPacket.getElements()[1]),
                            (String) serverInteractionPacket.getElements()[0]);
                }
                break;
            case END_ATTACK_S:
                LeaderBoard.getInstance().addBattleHistory(serverInteractionPacket.getID(),
                        (String) serverInteractionPacket.getElements()[0], (WarLog) serverInteractionPacket.getElements()[2]);
                Server.getInstance().sendToID(new ClientInteractionPacket(END_ATTACK_C, false,
                        serverInteractionPacket.getElements()[1]), (String) serverInteractionPacket.getElements()[0]);
                break;
        }
    }

    @Override
    public void receive(ClientInteractionPacket clientInteractionPacket) {
        switch (clientInteractionPacket.getBattleManagerPacketType()) {
            case VIEW_C:
                if (clientInteractionPacket.isReceiveRequest()) {
                    Client.getInstance().sendToServer(
                            new ServerInteractionPacket(VIEW_S, false,
                                    clientInteractionPacket.getElements()[0],
                                    JsonHandler.villageToJson(Client.getInstance().getAccount().getMyVillage())
                            ));
                } else {
                    requestedVillage.set(JsonHandler.jsonToVillage((String) clientInteractionPacket.getElements()[0]));
                }
                break;
            case ATTACK_C:
                requestedVillage.set(JsonHandler.jsonToVillage((String) clientInteractionPacket.getElements()[0]));
                break;
            case LOCK:
                isLocked = true;
                Client.getInstance().sendToServer(
                        new ServerInteractionPacket(ATTACK_S, false,
                                clientInteractionPacket.getElements()[0],
                                JsonHandler.villageToJson(Client.getInstance().getAccount().getMyVillage())
                        ));
                break;
            case END_ATTACK_C:
                // TODO: 7/16/2018 merging villages or ending attacks
                // TODO: 7/17/2018 id + json + warLog
                break;
        }
    }

    public Village getRequestedVillage() {
        return requestedVillage.get();
    }

    public ObjectProperty<Village> requestedVillageProperty() {
        return requestedVillage;
    }

    public void setRequestedAccount(AccountInfo accountInfo) {
        this.requestedAccount = accountInfo;
    }

    public AccountInfo getRequestedAccount() {
        return requestedAccount;
    }
}
