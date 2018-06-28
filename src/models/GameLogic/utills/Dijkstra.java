package models.GameLogic.utills;

import interfaces.Movable;
import interfaces.MovingAttacker;
import javafx.geometry.Pos;
import javafx.util.Pair;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Wall;
import models.GameLogic.GameMap;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;

import javax.swing.plaf.ButtonUI;
import java.util.*;

import static java.lang.Math.*;
import static models.GameLogic.Position.*;

public class Dijkstra {
    public static final double WALL_ATTACKER_MULTIPLIER = 2;
    public static final Integer MAX_NODE_DISTANCE = 1000000;

    public static ArrayList<Position> getPath(GameMap gameMap, MovingAttacker attacker, Position destination, int range) {
        if (!attacker.getTroopMoveType().equals(MoveType.GROUND)) {
            throw new RuntimeException("not ground troop");
        }
        Node[][] mapNodes = new Node[gameMap.getWidth()][gameMap.getHeight()];
        Building[][] buildings = new Building[gameMap.getMapWidth()][gameMap.getMapHeight()];
        Graph graph = initiateGraph(buildings, mapNodes, gameMap, attacker);
        calculateShortestPathFromSource(graph, mapNodes[attacker.getPosition().getX()][attacker.getPosition().getY()]);
        Node lastNode = findDestination(mapNodes, gameMap, destination, range);
        ArrayList<Position> path = new ArrayList<>();
        for(int i = 0; i < lastNode.getShortestPath().size(); i++){
            Node node = lastNode.getShortestPath().get(i);
            if(!(buildings[node.getPosition().getMapX()][node.getPosition().getMapY()] instanceof Wall)){
                path.add(node.getPosition());
            } else {
                attacker.setTarget(buildings[node.getPosition().getMapX()][node.getPosition().getMapY()]);
                for (int j = path.size()-1; j > 0 ; j--) {
                    if(path.get(j-1).calculateDistance(node.getPosition()) <= range) {
                        path.remove(j);
                    } else {
                        break;
                    }
                }

                return path;
            }
        }
        return path;
    }

    private static Graph initiateGraph(Building[][] buildings, Node[][] mapNodes, GameMap gameMap, MovingAttacker attacker) {
        Graph graph = new Graph();
        boolean[][] isThereWall = new boolean[gameMap.getMapWidth()][gameMap.getMapHeight()];
        for (Building building : gameMap.getBuildings()) {
            if (!building.isDestroyed()) {
                buildings[building.getPosition().getMapX()][building.getPosition().getMapY()] = building;
                if (building instanceof Wall) {
                    isThereWall[building.getPosition().getMapX()][building.getPosition().getMapY()] = true;
                }
            }

        }

        //making nodes
        for (int i = 0; i < gameMap.getMapWidth(); i++) {
            for (int j = 0; j < gameMap.getMapHeight(); j++) {
                Node mainNode = new Node(new Position(CELL_SIZE * i + 1, CELL_SIZE * j + 1), 1);
                for (int i1 = 0; i1 < CELL_SIZE; i1++) {
                    for (int j1 = 0; j1 < CELL_SIZE; j1++) {
                        int x = CELL_SIZE * i + i1;
                        int y = CELL_SIZE * j + j1;
                        if (!isThereWall[i][j]) {
                            if (buildings[i][j] == null) {
                                mapNodes[x][y] = new Node(new Position(x, y), 1);
                            } else {
                                if (abs(i1 - 1.5) < 1 && abs(j1 - 1.5) < 1) {
                                    mapNodes[x][y] = new Node(new Position(x, y), MAX_NODE_DISTANCE);
                                } else {
                                    mapNodes[x][y] = new Node(new Position(x, y), 1);
                                }
                            }
                        } else {
                            mainNode.setWeight(3 +
                                    (int) ceil(buildings[i][j].getHitPoints() / (attacker.getDamage() * WALL_ATTACKER_MULTIPLIER)));
                            mapNodes[x][y] = mainNode;
                        }
                    }
                }

            }
        }

        //adding edges to graph and putting nodes im it
        for (int i = 0; i < gameMap.getMapWidth(); i++) {
            for (int j = 0; j < gameMap.getMapHeight(); j++) {
                for (int i1 = 0; i1 < CELL_SIZE; i1++) {
                    for (int j1 = 0; j1 < CELL_SIZE; j1++) {
                        int x = CELL_SIZE * i + i1;
                        int y = CELL_SIZE * j + j1;
                        graph.addNode(mapNodes[x][y]);
                        for (Position dir : PathFinder.DIRECTIONS) {
                            Position neighbour = Position.addPositions(new Position(x, y), dir);
                            if (neighbour.isInBoundary(gameMap)) {
                                if(mapNodes[x][y] != mapNodes[neighbour.getX()][neighbour.getY()])
                                mapNodes[x][y].addDestination(mapNodes[neighbour.getX()][neighbour.getY()]);
                            }
                        }
                    }
                }
            }
        }
        return graph;
    }

    private static Node findDestination(Node[][] mapNodes, GameMap gameMap, Position target, int range){
        int minDistance = Integer.MAX_VALUE;
        Node minNode = null;
        for(int i = -range; i <= range; i++) {
            for(int j = -range; j <= range; j++){
                Position dir = new Position(i, j);
                Position position = Position.addPositions(target, dir);
                if(position.isInBoundary(gameMap) && target.calculateDistance(position) <= range) {
                    if(mapNodes[position.getX()][position.getY()].getDistance() < minDistance) {
                        minDistance = mapNodes[position.getX()][position.getY()].getDistance();
                        minNode = mapNodes[position.getX()][position.getY()];
                    }
                }
            }
        }
        return minNode;
    }

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>();
        PriorityQueue<Node> unsettledNodes = new PriorityQueue<>(Node.nodeComparator());
        unsettledNodes.add(source);
        while (unsettledNodes.size() != 0) {
            Node currentNode = unsettledNodes.poll();
            unsettledNodes.remove(currentNode);
            for (Node adjacentNode :
                    currentNode.getAdjacentNodes()) {
                Integer nodeWeight = adjacentNode.getWeight();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, nodeWeight, currentNode);
                    if(!unsettledNodes.contains(adjacentNode)) {
                        unsettledNodes.add(adjacentNode);
                    }
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Integer nodeWeight, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + nodeWeight < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + nodeWeight);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

}

class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }


}

class Node {
    private Position position;
    private int weight;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Set<Node> adjacentNodes = new HashSet<>();

    public void addDestination(Node destination) {
        adjacentNodes.add(destination);
    }

    public Node(Position position, int weight) {
        this.position = position;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Set<Node> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Set<Node> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public static Comparator<Node> nodeComparator(){
        return Comparator.comparing(Node::getDistance);
    }
}


