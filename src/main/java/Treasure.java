class Treasure extends RoomContents {

    private String treasureType;

    int getValue() {
        return value;
    }

    private final int value;

    Treasure(String treasureType) {
        this.treasureType = treasureType;
        value = assignValue(treasureType);

    }

    String getTreasureType() {
        return treasureType;
    }

    private int assignValue(String treasureType) {

        switch (treasureType) {
            case "silver coin":
                return 5;
            case "gold coin":
                return 10;
            case "diamond":
                return 25;
            case "chest":
                return 50;
            default:
                return 0;
        }
    }
}
