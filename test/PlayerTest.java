import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayerTest {


    @Test
    public void correctValueShouldBeAddedToPlayerWealthUponCollectingTreasure() {

        //Arrange
        Treasure diamond = new Treasure("diamond");
        Room room = new Room(diamond, "NE", 0, true);
        Player player = new Player(room);
        assert player.getWealth() == 0;

        //Act
        player.collectTreasure();

        //Assert
        System.out.println("TEST DATA: Expected player wealth = " + 25);
        System.out.println("TEST DATA: Actual player wealth = " + player.getWealth());
        assert player.getWealth() == diamond.getValue();
    }

    @Test
    public void shouldCorrectlyIdentifyIfRoomHasThreatOrTreasure() {

        //Arrange
        Treasure diamond = new Treasure("diamond");
        Threat skeleton = new Threat("skeleton");

        Room room1 = new Room(diamond, "NE", 0, true);
        Player player1 = new Player(room1);

        Room room2 = new Room(skeleton, "NE", 1, true);
        Player player2 = new Player(room2);

        //Act
        boolean room1ContainsTreasure = player1.currentRoomHasTreasure();
        boolean room1ContainsThreat = player1.currentRoomHasThreat();
        boolean room2ContainsTreasure = player2.currentRoomHasTreasure();
        boolean room2ContainsThreat = player2.currentRoomHasThreat();

        //Assert

        System.out.println("TEST DATA: Expected: room1ContainsTreasure = true");
        System.out.println("TEST DATA: Actual: room1ContainsTreasure = " + room1ContainsTreasure);
        System.out.println("TEST DATA: Expected: room2ContainsThreat = true");
        System.out.println("TEST DATA: Actual: room2ContainsThreat = " + room2ContainsThreat);
        assert room1ContainsTreasure;
        assert !room1ContainsThreat;
        assert !room2ContainsTreasure;
        assert room2ContainsThreat;

    }

    @Test
    public void shouldLeaveAnEmptyRoomWhenThreatIsDefeated() {

        //Arrange
        Room room = new Room(new Threat("skeleton"), "NE", 0, true);
        Player player = new Player(room);

        //Act
        player.attackEnemy(room.getContentsOfRoom(), "club");

        //Assert
        System.out.println("TEST DATA: Expected: Room contents after defeating threat = null");
        System.out.println("TEST DATA: Actual: Room contents after defeating threat = " + player.currentRoom.getContentsOfRoom());
        assert player.currentRoom.getContentsOfRoom() == null;
    }

    @Test
    void threatShouldRemainIfWrongWeaponIsPicked() {

        //Arrange
        Threat skeleton = new Threat("skeleton");
        Room room = new Room(skeleton, "NE", 0, true);
        Player player = new Player(room);

        //Act
        player.attackEnemy(room.getContentsOfRoom(), "bow");

        //Assert
        System.out.println("TEST DATA: Expected: Room contents after missing threat = skeleton");
        System.out.println("TEST DATA: Actual: Room contents after missing threat = "
                +  ((Threat) player.currentRoom.getContentsOfRoom()).getThreatType());
        assert player.currentRoom.getContentsOfRoom() == skeleton;

    }

    @Test
    void directionInputShouldIgnoreCaseAndWhiteSpace() {
        //Arrange
        String directionInput1 = "NoRTh";
        String directionInput2 = "   north ";

        //Act
        String result1 = Player.normaliseDirectionInput(directionInput1);
        String result2 = Player.normaliseDirectionInput(directionInput2);

        //Assert

        System.out.println("TEST DATA: Expected: Normalised input = North, North");
        System.out.println("TEST DATA: Actual: Normalised input = " + result1 + ", " + result2);
        assert result1.equals("North");
        assert result2.equals("North");
    }

    @Test
    void shouldGetDirectionInCorrectFormat() {

        //Arrange
        List<String> acceptedNorthFormats = new ArrayList<>();
        acceptedNorthFormats.add("n");
        acceptedNorthFormats.add("north");
        acceptedNorthFormats.add("up");

        List<String> acceptedEastFormats = new ArrayList<>();
        acceptedEastFormats.add("e");
        acceptedEastFormats.add("east");
        acceptedEastFormats.add("right");

        List<String> acceptedSouthFormats = new ArrayList<>();
        acceptedSouthFormats.add("s");
        acceptedSouthFormats.add("south");
        acceptedSouthFormats.add("down");

        List<String> acceptedWestFormats = new ArrayList<>();
        acceptedWestFormats.add("w");
        acceptedWestFormats.add("west");
        acceptedWestFormats.add("left");

        //Act
        List<String> actualNorthResults = getNormalisedResults(acceptedNorthFormats);
        List<String> actualEastResults = getNormalisedResults(acceptedEastFormats);
        List<String> actualSouthResults = getNormalisedResults(acceptedSouthFormats);
        List<String> actualWestResults = getNormalisedResults(acceptedWestFormats);

        //Assert
        System.out.println("TEST DATA: Expected: Directions: = North, North, North");
        System.out.println("TEST DATA: Actual: Directions: = " + actualNorthResults.toString());
        for (String result : actualNorthResults) {
            assert result.equals("North");
        }
        for (String result : actualEastResults) {
            assert result.equals("East");
        }
        for (String result : actualSouthResults) {
            assert result.equals("South");
        }
        for (String result : actualWestResults) {
            assert result.equals("West");
        }
    }

    private List<String> getNormalisedResults(List<String> stringToNormalise) {
        List<String> results = new ArrayList<>();
        for (String string : stringToNormalise) {
            results.add(Player.normaliseDirectionInput(string));
        }
        return results;
    }

    @Test
    void playerShouldMoveToCorrectNewRoom() {

        //Arrange
        String mazeFile = "src/resources/easyMaze.json";
        Maze maze = new Maze();
        maze.generateMaze(mazeFile);
        Player player = new Player(maze.getMaze().get(6));

        //Act
        player.movePlayer("North", 5);

        //Assert
        System.out.println("TEST DATA: Expected: Current room number after move = 1");
        System.out.println("TEST DATA: Actual: Current room number after move = " + player.currentRoom.getRoomNumber());
        assert player.currentRoom.equals(player.getMaze().get(1));
        assert player.currentRoom.getRoomNumber() == 1;
    }


    @Test
    void shouldLoseCorrectAmountOfWealthWhenHitByEnemy() {

        //Arrange
        Room room = new Room();

        Player player1 = new Player(room);
        player1.setWealth(5);

        Player player2 = new Player(room);
        player2.setWealth(15);

        //Act
        player1.getHitByEnemy();
        player2.getHitByEnemy();

        //Assert
        System.out.println("TEST DATA: Expected: Player 1 wealth after hit = 0");
        System.out.println("TEST DATA: Actual: Player 1 wealth after hit = " + player1.getWealth());
        System.out.println("TEST DATA: Expected: Player 2 wealth after hit = 5");
        System.out.println("TEST DATA: Actual: Player 2 wealth after hit = " + player2.getWealth());
        assert player1.getWealth() == 0;
        assert player2.getWealth() == 5;


    }



}
