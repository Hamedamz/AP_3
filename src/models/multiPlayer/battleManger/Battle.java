package models.multiPlayer.battleManger;

import models.AccountInfo;
import models.GameLogic.Bounty;
import models.GameLogic.Resource;

import java.util.UUID;

public class Battle {
    private String battleID;

    private AccountInfo attacker;
    private AccountInfo defender;

    private Bounty lootedBounty;

    public Battle(AccountInfo attacker, AccountInfo defender) {
        this.attacker = attacker;
        this.defender = defender;
        battleID = UUID.randomUUID().toString();
        lootedBounty = new Bounty(0, new Resource(0, 0));
    }

    public boolean containsAccountID(String id) {
        return attacker.getId().equals(id) || defender.getId().equals(id);
    }

    public String getBattleID() {
        return battleID;
    }

    public AccountInfo getAttacker() {
        return attacker;
    }

    public AccountInfo getDefender() {
        return defender;
    }

    public Bounty getLootedBounty() {
        return lootedBounty;
    }
}
