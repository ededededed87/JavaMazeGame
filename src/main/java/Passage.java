class Passage extends Room {

    private String direction;

    String getDirection() {
        return direction;
    }

    Passage(String direction, boolean isExit) {
        this.direction = direction;
        this.isExit = isExit;
    }

}
