import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String easyMaze = "src/resources/easyMaze.json";
    private static final String hardMaze = "src/resources/hardMaze.json";
    private static String mazeFile = easyMaze;

    public static void main(String[] args) {

        boolean continuePlaying = true;
        boolean atExitFlag = true;
        boolean sameGameContinuing;
        printHelpMessage();


        while (continuePlaying) {

            Maze maze = new Maze();
            maze.generateMaze(mazeFile);

            int mazeWidth = mazeFile.equals(easyMaze) ? 5 : 7;

            List<Room> acceptableStartingRooms = getAcceptableStartingPoints(maze);
            int randomIndex = (int) (Math.random() * (acceptableStartingRooms.size()));

            Room randomStartingRoom = acceptableStartingRooms.get(randomIndex);
            Player player = new Player(randomStartingRoom);

            sameGameContinuing = true;

            while (sameGameContinuing) {


                while (!player.currentRoom.isExit()) {


                    player.printCurrentRoomInfo();
                    while (player.currentRoomHasThreat()) {
                        String playerAction = player.getRunOrFight();

                        if (playerAction.equals("fight")) {
                            player.fight();
                        }
                        else if (playerAction.equals("run")) {
                            player.runAway(mazeWidth);
                        }
                    }

                    if (player.currentRoomHasTreasure()) {
                        player.collectTreasure();
                    }

                    String playerMove = player.getPlayerMove();
                    if (playerMove.equals("quit")) {
                        sameGameContinuing = false;
                        continuePlaying = false;
                        atExitFlag = false;
                        break;
                    }
                    else if (playerMove.equals("restart")) {
                        sameGameContinuing = false;
                        atExitFlag = false;
                        break;
                    }
                    else if (playerMove.equals("help")) {
                        printHelpMessage();
                        break;
                    }
                    else {
                        player.movePlayer(playerMove, mazeWidth);
                    }
                }

                if (atExitFlag) {
                    System.out.println("Congratulations! You have found the exit!");

                    System.out.println("Your wealth is " + player.getWealth());
                    System.out.println("Would you like to turn back(keep exploring the maze), play again or quit?");
                    String endGameChoice = getEndGameChoice();

                    switch (endGameChoice) {
                        case "turn back":
                            System.out.println("You turn around and head back into the maze to search for more treasure.");
                            player.movePlayer(player.getOppositeDirection(player.getLastDirectionTravelled()), mazeWidth);
                            break;

                        case "play again":
                            System.out.println("Which difficulty would you like to play? Easy or Hard?");
                            String difficultyChoice = getDifficultyChoice();
                            if (difficultyChoice.equals("hard")) {
                                mazeFile = hardMaze;
                            }
                            sameGameContinuing = false;
                            break;

                        case "quit":
                            sameGameContinuing = false;
                            continuePlaying = false;
                            break;
                    }
                }
            }
        }
    }

    private static List<Room> getAcceptableStartingPoints(Maze maze) {
        List<Room> rooms = maze.getMaze();
        List<Room> acceptableStartingPoints = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCanStartHere()) {
                acceptableStartingPoints.add(room);
            }
        }
        return acceptableStartingPoints;
    }

    private static void printHelpMessage() {
        System.out.println("Your goal is to acquire as much wealth as possible and find the exit to the maze.");
        System.out.println("You may encounter enemies along the way. If you can't defeat an enemy, explore the maze more. You may find something that will help you.");
        System.out.println("You may quit or restart at any point by typing quit or restart on the passage selection screen.");
        System.out.println("Enter inputs as they appear in the prompts.");
        System.out.println("For directions you may type n, north or up to go north. These formats work for each direction.");
        System.out.println("Inputs are not case sensitive.");
        System.out.println("To see this message again, type help on the passage selection screen.");
    }

    private static String getDifficultyChoice() {
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine().toLowerCase().trim();

        while (!choice.equals("easy") && !choice.equals("hard")) {
            System.out.println("You must choose easy or hard.");
            System.out.println("Which difficulty would you like to play?");
            choice = input.nextLine().toLowerCase();
        }
        return choice;
    }

    private static String getEndGameChoice() {
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine().toLowerCase().trim();

        while (!choice.equals("turn back") && !choice.equals("play again") && !choice.equals("quit")) {
            System.out.println("You must choose turn back, play again or quit");
            System.out.println("Would you like to turn back(keep exploring the maze), play again or quit?");
            choice = input.nextLine().toLowerCase();
        }
        return choice;
    }
}