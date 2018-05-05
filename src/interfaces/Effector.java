package interfaces;

import models.GameLogic.Exceptions.NoTargetFoundException;

import java.util.ArrayList;

public interface Effector<T> {
    int getEffectRange();
    void setTarget(ArrayList<T> tList) throws NoTargetFoundException;
    T getTarget();
}
