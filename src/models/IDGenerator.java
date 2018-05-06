package models;

public class IDGenerator {
    private ID lastID;
    private final ID initialID;

    public IDGenerator(String firstPartCode, int jsonNumber) {
        initialID = new ID(firstPartCode, jsonNumber, 0);
        lastID = initialID;
    }

    public int getJsonNumber() {
        return initialID.getJsonNumber();
    }

    /**
     * count is 1_based
     * @return
     */
    public ID getNewID() {
        lastID = new ID(lastID.getFirstPartCode(), lastID.getJsonNumber(), lastID.getCount() + 1);
        return lastID;
    }

    public void reset() {
        lastID = initialID;
    }
}
