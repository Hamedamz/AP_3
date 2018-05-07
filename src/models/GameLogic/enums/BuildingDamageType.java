package models.GameLogic.enums;

public enum BuildingDamageType {
    AREA_SPLASH, SINGLE_TARGET;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
