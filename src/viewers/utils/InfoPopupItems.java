package viewers.utils;

import java.util.Arrays;
import java.util.List;

import static viewers.utils.ProgressBarType.*;
import static viewers.utils.PropertyInfoType.*;

public enum InfoPopupItems {
    AirDefenseInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL, TARGET_TYPE, DAMAGE_TYPE, DAMAGE_PER_HIT, RANGE)),
    ArcherTowerInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL, TARGET_TYPE, DAMAGE_TYPE, DAMAGE_PER_HIT, RANGE)),
    BarracksInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL)),
    CampInfo(Arrays.asList(HIT_POINTS_INFO, TROOPS_CAPACITY_INFO), Arrays.asList(LEVEL)),
    CannonInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL, TARGET_TYPE, DAMAGE_TYPE, DAMAGE_PER_HIT, RANGE)),
    ElixirMineInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL, PRODUCTION_RATE)),
    ElixirStorageInfo(Arrays.asList(HIT_POINTS_INFO, ELIXIR_INFO), Arrays.asList(LEVEL)),
    GoldMineInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL, PRODUCTION_RATE)),
    GoldStorageInfo(Arrays.asList(HIT_POINTS_INFO, GOLD_INFO), Arrays.asList(LEVEL)),
    GuardianGiantInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL, TARGET_TYPE, DAMAGE_TYPE, DAMAGE_PER_HIT, RANGE)),
    TownHallInfo(Arrays.asList(HIT_POINTS_INFO, GOLD_INFO, ELIXIR_INFO), Arrays.asList(LEVEL)),
    TrapInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL, TARGET_TYPE, DAMAGE_TYPE, DAMAGE_PER_HIT, RANGE)),
    WallInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL)),
    WizardTowerInfo(Arrays.asList(HIT_POINTS_INFO), Arrays.asList(LEVEL, TARGET_TYPE, DAMAGE_TYPE, DAMAGE_PER_HIT, RANGE));

    private List<ProgressBarType> progressBarTypes;
    private List<PropertyInfoType> propertyInfoTypes;

    InfoPopupItems(List<ProgressBarType> progressBarTypes, List<PropertyInfoType> propertyInfoTypes) {
        this.progressBarTypes = progressBarTypes;
        this.propertyInfoTypes = propertyInfoTypes;
    }

    public List<ProgressBarType> getProgressBarTypes() {
        return progressBarTypes;
    }

    public List<PropertyInfoType> getPropertyInfoTypes() {
        return propertyInfoTypes;
    }
}
