package interfaces;

import models.GameLogic.Resource;

public interface Upgradable {
    void upgrade();
    Resource getUpgradeResource();
}
