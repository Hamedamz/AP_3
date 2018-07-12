package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Builder;
import models.GameLogic.Exceptions.NoSuchAUnderConstructBuildingException;
import models.GameLogic.Exceptions.NoFreeBuilderException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.utills.IDGenerator;
import models.setting.GameLogicConfig;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;

import java.util.ArrayList;

public class TownHall extends Storage {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 5);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 5);

    private ArrayList<Builder> builders;
    private int villageScore;
    private int townHallScore;

    public TownHall(Position position, boolean isFriendly){
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID() );
        townHallScore = GameLogicConfig.getFromDictionary("TownHallDestructionScore");
        builders = new ArrayList<>();
        builders.add(new Builder());
        villageScore = 0;
        //jsonNumber = 5;
    }

    public ArrayList<Builder> getBuilders() {
        return builders;
    }

    public int getVillageScore() {
        return villageScore;
    }

    @Override
    public void upgrade() {
        if (level % 5 == 4) {
            builders.add(new Builder());
        }
        this.setLevel(this.getLevel() + 1);
        Building.setMaxLevel(level);
        int hitPointsAddition = (int) GameLogicConfig.getFromDictionary("TownHallUpgradeHitPointsAddition");
        this.setHitPoints(this.hitPoints + hitPointsAddition);
        updateViewPort();
        try {
            SoundPlayer.play(Sounds.buildCompleteSound);
        }
        catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Bounty getBounty() {
        int gold = GameLogicConfig.getFromDictionary("TownHallDestructionGold");
        int elixir = GameLogicConfig.getFromDictionary("TownHallDestructionElixir");
        Resource resource = new Resource(gold, elixir);
        int score = this.townHallScore;
        return new Bounty(score, resource);
    }

    public void addScore(int score) {
        this.villageScore += score;
    }


    public Builder getFreeBuilder() throws NoFreeBuilderException {
        for (Builder builder : builders) {
            if (!builder.isBuilderBusy()) {
                return builder;
            }
        }
        throw new NoFreeBuilderException();
    }

    public Builder getBuilder(Position position) throws NoSuchAUnderConstructBuildingException {
        for(Builder builder : builders) {
            if (builder.isBuilderBusy()) {
                if(builder.getUnderConstructBuilding().getPosition().equals(position)) {
                    return builder;
                }
            }
        }
        throw new NoSuchAUnderConstructBuildingException();
    }
}
