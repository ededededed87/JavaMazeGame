import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class Room extends Maze {

    boolean isExit = false;
    private long roomNumber;
    private Boolean canStartHere;
    private RoomContents contentsOfRoom;
    List<Passage> availablePassages = new ArrayList<>();

    Room() {
    }

    Room(RoomContents contentsOfRoom, String passages, long roomNumber, boolean canStartHere) {
        this.roomNumber = roomNumber;
        this.contentsOfRoom = contentsOfRoom;
        this.canStartHere = canStartHere;
        addPassages(passages);
        if (passages.length() == 1) {
            isExit = true;
        }
    }

    long getRoomNumber() {
        return roomNumber;
    }

    boolean isExit() {
        return isExit;
    }

    Boolean getCanStartHere() {
        return canStartHere;
    }

    RoomContents getContentsOfRoom() {
        return contentsOfRoom;
    }

    List<Passage> getAvailablePassages() {
        return availablePassages;
    }

    void setContentsOfRoom(RoomContents contentsOfRoom) {
        this.contentsOfRoom = contentsOfRoom;
    }

    void addPassages(String passageString) {

        if (isValidPassageString(passageString)) {
            for (int i = 0; i < passageString.length(); i++) {
                String direction = getDirectionFromLetter(passageString.charAt(i));
                boolean isExit = passageString.length() == 1;
                availablePassages.add(new Passage(direction, isExit));
            }
        }
    }

    static String getDirectionFromLetter(char directionLetter) {
        switch (directionLetter) {
            case 'N':
                return "North";
            case 'E':
                return "East";
            case 'S':
                return "South";
            case 'W':
                return "West";
            default:
                return null;

        }
    }

    static Boolean isValidPassageString(String passageString) {

        String upperCasePassageString = passageString.toUpperCase();
        Set<String> passages = new HashSet<>();

        List<String> acceptableCharacters = new ArrayList<>();
        acceptableCharacters.add("N");
        acceptableCharacters.add("E");
        acceptableCharacters.add("S");
        acceptableCharacters.add("W");

        if (upperCasePassageString.length() < 1 || upperCasePassageString.length() > 4) {
            return false;
        }

        for (int i = 0; i < upperCasePassageString.length(); i++) {

            String character = String.valueOf(upperCasePassageString.charAt(i));

            if (!acceptableCharacters.contains(character)) {
                return false;
            }

            //Set.add() returns false if the element is already present in the set
            if (!passages.add(character)) {
                return false;
            }
        }

        return true;
    }

    List<String> getPassageDirections() {
        List<String> passageDirections = new ArrayList<>();
        for (Passage passage : availablePassages) {
            passageDirections.add(passage.getDirection());
        }
        return passageDirections;
    }

}
