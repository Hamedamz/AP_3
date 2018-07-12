package models.GameLogic.utills;

import interfaces.MovingAttacker;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Wall;
import models.GameLogic.GameMap;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;

import java.util.*;

import static java.lang.Math.*;
import static models.GameLogic.Position.*;

public class Dijkstra {
    public static final double WALL_ATTACKER_MULTIPLIER = 2;
    public static final Integer MAX_NODE_DISTANCE = 10000;

    public static ArrayList<Position> findMinPath(GameMap gameMap, MovingAttacker attacker, Position destination, int range) {
        if (!attacker.getTroopMoveType().equals(MoveType.GROUND)) {
            throw new RuntimeException("not ground troop");
        }
        Node[][] mapNodes = new Node[gameMap.getWidth()][gameMap.getHeight()];
        Graph graph = initiateGraph(mapNodes, gameMap, attacker);
        calculateShortestPathFromSource(graph, mapNodes[attacker.getPosition().getX()][attacker.getPosition().getY()]);
        Node lastNode = findDestination(mapNodes, gameMap, destination, range);
        return findMinPath(attacker, range, lastNode);
    }

    private static Graph initiateGraph(Node[][] mapNodes, GameMap gameMap, MovingAttacker attacker) {
        Graph graph = new Graph();

        //making nodes
        for (int i = 0; i < gameMap.getMapWidth(); i++) {
            for (int j = 0; j < gameMap.getMapHeight(); j++) {
                for (int i1 = 0; i1 < CELL_SIZE; i1++) {
                    for (int j1 = 0; j1 < CELL_SIZE; j1++) {
                        int x = CELL_SIZE * i + i1;
                        int y = CELL_SIZE * j + j1;
                        mapNodes[x][y] = new Node(new Position(x, y), 1, false);
                    }
                }

            }
        }
        for(Building building : gameMap.getBuildings()) {
            if(!building.isDestroyed()) {
                Node wallNode = new Node(building.getPosition(), 3 +
                        (int) ceil(building.getHitPoints() / (attacker.getDamage() * WALL_ATTACKER_MULTIPLIER)), true);
                for (int i1 = 0; i1 < building.getSize() * CELL_SIZE; i1++) {
                    for (int j1 = 0; j1 < building.getSize() * CELL_SIZE; j1++) {
                        int x = CELL_SIZE * building.getPosition().getMapX() + i1;
                        int y = CELL_SIZE * building.getPosition().getMapY() + j1;
                        if (building instanceof Wall) {
                            if((i1 == 0 || i1 == building.getSize() * CELL_SIZE - 1) && (j1 == 0 || j1 == building.getSize() * CELL_SIZE - 1)) {
                                mapNodes[x][y] = new Node(new Position(x, y), 1, false);
                            } else {
                                mapNodes[x][y] = wallNode;
                                wallNode.setWall((Wall) building);
                            }
                        } else {
                            if((i1 == 0) || (i1 == building.getSize() * CELL_SIZE - 1)
                                    || (j1 == 0) || (j1 == building.getSize() * CELL_SIZE - 1)) {
                                mapNodes[x][y] = new Node(new Position(x, y), 1, false);
                            } else {
                                mapNodes[x][y] = new Node(new Position(x, y), MAX_NODE_DISTANCE, false);
                            }
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

    private static ArrayList<Position> findMinPath(MovingAttacker attacker, int range, Node lastNode) {
        ArrayList<Position> path = new ArrayList<>();
        lastNode.getShortestPath().add(lastNode);
        for(int i = 0; i < lastNode.getShortestPath().size(); i++){
            Node node = lastNode.getShortestPath().get(i);
            if(!node.isWall()){
                path.add(node.getPosition());
            } else {
                attacker.setTarget(lastNode.getWall());
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

    private int distance = Integer.MAX_VALUE;

    Set<Node> adjacentNodes = new HashSet<>();

    private boolean isWall;
    private Wall wall;

    public void addDestination(Node destination) {
        adjacentNodes.add(destination);
    }

    public Node(Position position, int weight, boolean isWall) {
        this.position = position;
        this.weight = weight;
        this.isWall = isWall;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isWall() {
        return isWall;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
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


