class Threat extends RoomContents {

    private String isDefeatedBy;
    String threatType;

    Threat(String threatType) {
        this.threatType = threatType;
        isDefeatedBy = getWeapon(threatType);
    }

    String getDefeatedBy() {
        return isDefeatedBy;
    }

    String getThreatType() {
        return threatType;
    }

    static String getFailureMessage(String threatType) {
        switch (threatType) {

            case "skeleton":
                return "You shoot your bow at the skeleton but the arrow flies straight through it.";
            case "dragon":
                return "You swing your club at the dragon with all your might. Unfortunately, the dragon is flying and you miss by a long way.";
            default:
                return null;
        }
    }

    static String getWeapon(String threatType) {
        switch (threatType) {

            case "skeleton":
                return "club";
            case "dragon":
                return "bow";
            default:
                return null;
        }
    }

    static String getSuccessMessage(String threatType) {
        switch (threatType) {

            case "skeleton":
                return "You hit the skeleton with your club and the skeleton crumbles to dust.";
            case "dragon":
                return "You hit the dragon with an arrow and the dragon explodes in a ball of flame.";
            default:
                return null;
        }
    }
}
