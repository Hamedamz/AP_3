package models.multiPlayer.leaderBoard;

import models.GameLogic.Bounty;

import java.io.Serializable;

public class BattleHistory implements Serializable {
    private int battleTime;
    private String attackerName;
    private String defenderName;
    private Bounty lootedBounty;

    public int getBattleTime() {
        return battleTime;
    }

    public void setBattleTime(int battleTime) {
        this.battleTime = battleTime;
    }

    public String getAttackerName() {
        return attackerName;
    }

    public void setAttackerName(String attackerName) {
        this.attackerName = attackerName;
    }

    public String getDefenderName() {
        return defenderName;
    }

    public void setDefenderName(String defenderName) {
        this.defenderName = defenderName;
    }

    public int getGold() {
        return lootedBounty.getGold();
    }

    public int getElixir() {
        return lootedBounty.getElixir();
    }

    public int getScore() {
        return lootedBounty.getScore();
    }

    public void setLootedBounty(Bounty lootedBounty) {
        this.lootedBounty = lootedBounty;
    }
}
