package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.utills.IDGenerator;
import models.setting.GameLogicConfig;

public class Wall extends Building {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 12);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 12);

    public Wall(Position position, boolean isFriendly){
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("WallBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }
}
