package models.interfaces;

import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.Resource;

public interface Upgradable {
    void upgrade() throws UpgradeLimitReachedException;
    Resource getUpgradeResource() throws UpgradeLimitReachedException;
}
