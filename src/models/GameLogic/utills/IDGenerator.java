package models.GameLogic.utills;

import models.GameLogic.ID;

import java.util.HashSet;
import java.util.Set;

public class IDGenerator { // FIXME: 5/7/2018 reset after new village
    private static Set<IDGenerator> idGenerators;

    static {
        idGenerators = new HashSet<>();
    }

    private ID lastID;
    private final ID initialID;

    public IDGenerator(String firstPartCode, int jsonNumber) {
        initialID = new ID(firstPartCode, jsonNumber, 0);
        lastID = initialID;
        idGenerators.add(this);
    }

    public static void resetIDGenerator()
    {
        for (int i = 0; i < idGenerators.size(); i++) {
            for (IDGenerator idGenerator : idGenerators) {
                idGenerator.reset();
            }
        }
    }
    public int getJsonNumber() {
        return initialID.getJsonNumber();
    }

    /**
     * count is 1_based
     * @return
     */
    public ID getNewID() {
        lastID = new ID(lastID.getFirstPartCode(), lastID.getJsonNumber(), (lastID.getCount() + 1) % 10000);
        return lastID;
    }

    public void reset() {
        lastID = initialID;
    }
}
