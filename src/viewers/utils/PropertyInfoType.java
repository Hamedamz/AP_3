package viewers.utils;

public enum PropertyInfoType {
    LEVEL,
    TARGET_TYPE,
    DAMAGE_TYPE,
    DAMAGE_PER_HIT,
    RANGE,
    PRODUCTION_RATE;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replaceAll("_", " ");
    }
}
