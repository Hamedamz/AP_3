package models.GameLogic;

public final class ID {
    private final String firstPartCode;
    private final String jsonNumber;
    private final String count;

    public ID(String firstPartCode, int jsonNumber, int count){
        String jsonString = String.valueOf(jsonNumber);
        if (!jsonString.matches("\\d+") || !firstPartCode.matches("\\d+")) {
            throw new RuntimeException("Invalid ID inputs");
        }
        while (firstPartCode.length() < 2) {
            firstPartCode = "0" + firstPartCode;
        }
        while (jsonString.length() < 2) {
            jsonString = "0" + jsonString;
        }
        this.firstPartCode = firstPartCode;
        this.jsonNumber = jsonString;
        String countStr = String.valueOf(count);
        while (countStr.length() < 4) {
            countStr = "0" + countStr;
        }
        this.count = countStr;
    }

    @Override
    public String toString() {
        return firstPartCode + jsonNumber + count;
    }

    public String getFirstPartCode() {
        return firstPartCode;
    }

    public int getJsonNumber() {
        return Integer.parseInt(jsonNumber);
    }

    public int getCount() {
        return Integer.valueOf(count);
    }
}
