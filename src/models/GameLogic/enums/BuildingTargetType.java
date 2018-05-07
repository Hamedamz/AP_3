package models.GameLogic.enums;

public enum BuildingTargetType {
    GROUND_AND_AIR, GROUND, AIR;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
