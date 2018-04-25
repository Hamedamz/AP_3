package models.GameLogic;

public class TrainingTroop {
    private int totalTime;
    private int timeRemaining;
    private Resource buildingResource;
    public String troopType;

    public TrainingTroop(String troopType, int barracksLevel) {

    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public Resource getBuildingResource() {
        return buildingResource;
    }

    public String getTroopType() {
        return troopType;
    }

    public boolean hasEndedTraining() {

    }
}
