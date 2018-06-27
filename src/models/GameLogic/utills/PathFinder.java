package models.GameLogic.utills;

import interfaces.Movable;
import interfaces.MovingAttacker;
import models.GameLogic.GameMap;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;

import java.util.ArrayList;

public class PathFinder {
    public static final Double EPSILON;
    public static Position[] DIRECTIONS;

    static {
        EPSILON = 1e-6;
        Position DIR_UP = new Position(0, -1);
        Position DIR_RIGHT = new Position(1, 0);
        Position DIR_DOWN = new Position(0, 1);
        Position DIR_LEFT = new Position(-1, 0);
        DIRECTIONS = new Position[]{DIR_UP, DIR_RIGHT, DIR_DOWN, DIR_LEFT};
    }

    public static ArrayList<Position> getPath(GameMap gameMap, Movable movable, Position destination, int range){
        if(movable.getTroopMoveType().equals(MoveType.AIR) || movable.getTroopMoveType().equals(MoveType.JUMPER)) {
            return BFS.getPath(gameMap, movable.getPosition(), destination, range);
        } else {
            //we assume that every Ground Movable Effector is an attacker
            if(movable instanceof MovingAttacker) {
                return Dijkstra.getPath(gameMap, ((MovingAttacker) movable), destination, range);
            }
            throw new RuntimeException("a Ground Movable effector which is not MovingAttacker");
        }
    }
}
