package models.multiPlayer.leaderBoard;

import models.GameLogic.Bounty;

public class BattleHistory {
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

    public Bounty getLootedBounty() {
        return lootedBounty;
    }

    public void setLootedBounty(Bounty lootedBounty) {
        this.lootedBounty = lootedBounty;
    }
}
