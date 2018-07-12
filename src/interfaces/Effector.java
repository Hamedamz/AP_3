package interfaces;

import models.GameLogic.Exceptions.NoTargetFoundException;

import java.util.ArrayList;

public interface Effector<T> {
    int getEffectRange();
    void findTarget(ArrayList<T> tList) throws NoTargetFoundException;
    T getTarget();
    void removeTarget();
}
