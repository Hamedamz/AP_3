package interfaces;

import models.Entity;
import models.Map;

public interface Movable {
    void move(Entity entity, Map map);
}
