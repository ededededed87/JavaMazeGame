
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player extends Maze {

    Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.currentRoomNumber = currentRoom.getRoomNumber();
        this.availableWeapons.add("club");
    }

    private int wealth = 0;
    Room currentRoom;
    private long currentRoomNumber;
    private String lastDirectionTravelled;
    private List<String> availableWeapons = new ArrayList<>();


    int getWealth() {
        return wealth;
    }

    String getLastDirectionTravelled() {
        return lastDirectionTravelled;
    }

    boolean currentRoomHasThreat() {
        return currentRoom.getContentsOfRoom() instanceof Threat;
    }

    boolean currentRoomHasTreasure() {
        return currentRoom.getContentsOfRoom() instanceof Treasure;
    }

    void setWealth(int wealth) {
        this.wealth = wealth;
    }

    void movePlayer(String direction, int mazeWidth) {

        int roomModifier;

        switch (direction) {
            case "North":
                roomModifier = -mazeWidth;
                break;
            case "East":
                roomModifier = 1;
                break;
            case "South":
                roomModifier = mazeWidth;
                break;
            case "West":
                roomModifier = -1;
                break;
            default:
                roomModifier = 0;

        }
        int newRoomNumber = (int) currentRoomNumber + roomModifier;

        currentRoom = currentRoom.getMaze().get(newRoomNumber);
        currentRoomNumber = currentRoom.getRoomNumber();
        lastDirectionTravelled = direction;
    }

    void printCurrentRoomInfo() {
        if (currentRoom.isExit) {
            printExitMessage();
        }

        if (currentRoom.getContentsOfRoom() == null) {
            printEmptyRoomInfo();
        }

        if (currentRoom.getContentsOfRoom() != null) {
            printNonEmptyRoomInfo();
        }
    }

    private void printExitMessage() {
        System.out.println("Congratulations! You have found the exit!");
        System.out.println("Your current wealth is " + wealth);
    }

    private void printEmptyRoomInfo() {
        System.out.println("You enter a new room and look around.");
        System.out.println("The room is empty. Maybe you've been here before.");
    }

    private void printNonEmptyRoomInfo() {
        if (currentRoom.getContentsOfRoom() instanceof Threat) {
            System.out.println("You enter a new room and are immediately confronted by an angry " + ((Threat) currentRoom.getContentsOfRoom()).getThreatType());
        }
        if (currentRoom.getContentsOfRoom() instanceof Treasure) {
            System.out.println("You enter a new room and notice a " + ((Treasure) currentRoom.getContentsOfRoom()).getTreasureType() + " in the centre of the room.");
        }
    }

    private void printPassageOptions() {
        List<Passage> availablePassages = currentRoom.getAvailablePassages();
        System.out.println("There are " + availablePassages.size() + " passages leading out of the room:");
        for (Passage passage : availablePassages) {
            System.out.println(passage.getDirection());
        }
        System.out.println("Choose wisely.");
    }

    private void printFightInfo() {
        System.out.println("You are battling a " + ((Threat) currentRoom.getContentsOfRoom()).getThreatType());
        printAvailableWeapons();

    }

    private void printAvailableWeapons() {
        System.out.println("You have the following weapons available:");
        for (String weapon : availableWeapons) {
            System.out.println(weapon.substring(0, 1).toUpperCase() + weapon.substring(1));
        }
    }


    void fight() {
        printFightInfo();
        String weapon = chooseWeapon();
        attackEnemy(currentRoom.getContentsOfRoom(), weapon);
    }

    void attackEnemy(RoomContents contentsOfRoom, String weapon) {
        if (((Threat) contentsOfRoom).getDefeatedBy().toLowerCase().equals(weapon.toLowerCase())) {
            System.out.println(Threat.getSuccessMessage(((Threat) contentsOfRoom).threatType));
            defeatEnemy();
        } else {
            System.out.println(Threat.getFailureMessage(((Threat) contentsOfRoom).threatType));
            getHitByEnemy();
        }
    }

    void getHitByEnemy() {
        System.out.println("Unfazed by your attack, the monster strikes you square in the chest, causing you to drop some of your treasure.");
        //Wealth reduced by 10 or to 0 if they less than 10;
        int amountToLose = getWealth() > 10 ? 10 : getWealth();
        setWealth(getWealth() - amountToLose);
        System.out.println("Your wealth is now " + getWealth());
    }

    private void defeatEnemy() {
        currentRoom.setContentsOfRoom(null);
    }

    private String chooseWeapon() {
        Scanner input = new Scanner(System.in);
        System.out.println("Which weapon would you like to use?");
        String weaponChoice = input.nextLine().toLowerCase();
        while (!availableWeapons.contains(weaponChoice)) {
            System.out.println("You don't own a " + weaponChoice);
            printAvailableWeapons();
            System.out.println("Which weapon would you like to use?");
            weaponChoice = input.nextLine().toLowerCase();
        }
        return weaponChoice;
    }

    String getPlayerMove() {

        List<String> availableChoices = currentRoom.getPassageDirections();
        availableChoices.add("quit");
        availableChoices.add("restart");
        availableChoices.add("help");
        Scanner input = new Scanner(System.in);
        printPassageOptions();
        System.out.println("Which passage would you like to travel through?");
        String directionChoice = normaliseDirectionInput(input.nextLine());
        while (!availableChoices.contains(directionChoice)) {
            System.out.println("There is no passage that leads that way.");
            printPassageOptions();
            System.out.println("Which passage would you like to travel through?");
            directionChoice = normaliseDirectionInput(input.nextLine());
        }

        return directionChoice;

    }

    static String normaliseDirectionInput(String directionChoice) {
        String normalisedInput =
                directionChoice.toLowerCase().trim();

        switch (normalisedInput) {
            case "north":
            case "up":
            case "n":
                return "North";
            case "east":
            case "right":
            case "e":
                return "East";
            case "south":
            case "down":
            case "s":
                return "South";
            case "west":
            case "left":
            case "w":
                return "West";
            case "quit":
                return "quit";
            case "restart":
                return "restart";
            case "help":
                return "help";

            default:
                return normalisedInput;
        }
    }

    void collectTreasure() {
        if (currentRoomHasTreasure()) {
            Treasure treasure = (Treasure) currentRoom.getContentsOfRoom();
            wealth += treasure.getValue();
            if (treasure.getTreasureType().equals("bow")) {
                availableWeapons.add("bow");
            }
            System.out.println("You collect the treasure.");
            System.out.println("Your wealth has increased by " + treasure.getValue() + ".");
            System.out.println("Your wealth is now " + wealth + ".");
            System.out.println("You keep the " + treasure.getTreasureType() + " in case it comes in handy later.");

            currentRoom.setContentsOfRoom(null);
        }
    }

    String getRunOrFight() {
        Scanner input = new Scanner(System.in);
        printAvailableWeapons();
        System.out.println("Would you like to fight the monster or run?");
        String choice = input.nextLine().toLowerCase().trim();

        while (!choice.equals("run") && !choice.equals("fight")) {
            System.out.println("You must choose run or fight");
            System.out.println("Would you like to fight the monster or run?");
            choice = input.nextLine().toLowerCase();
        }
        return choice;
    }

    void runAway(int mazeWidth) {

        String lastDirectionTravelled = getLastDirectionTravelled() != null ? getLastDirectionTravelled() : "";
        String runAwayDirection = getOppositeDirection(lastDirectionTravelled);


        if (runAwayDirection != null) {
            System.out.println("You escape from the monster and run back through the passage you came from.");
            movePlayer(runAwayDirection, mazeWidth);
        }
        else {
            System.out.println("You have no where to run to. You must fight!");
            fight();
        }
    }

    String getOppositeDirection(String lastDirectionTravelled) {
        switch (lastDirectionTravelled) {
            case "North":
                return "South";
            case "East":
                return "West";
            case "South":
                return "North";
            case "West":
                return "East";
            default:
                return null;

        }
    }
}


