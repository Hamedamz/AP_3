package controllers.enums;

public enum GeneralCommand {
    SAVE_GAME,
    RESOURCES;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
