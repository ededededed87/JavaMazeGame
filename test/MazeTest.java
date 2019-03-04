import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MazeTest {

    String mazeFile = "src/resources/easyMaze.json";

    @Test
    public void shouldGenerateMazeOfSize26() {

        //Arrange
        Maze maze = new Maze();

        //Act
        maze.generateMaze(mazeFile);

        //Assert
        System.out.println("TEST DATA: Expected size of maze = " + 26);
        System.out.println("TEST DATA: Actual size of maze = " + maze.getMaze().size());
        assert maze.getMaze().size() == 26;
    }

    @Test
    void eachRoomShouldHaveCorrectRoomNumber() {

        //Arrange
        Maze maze = new Maze();
        maze.generateMaze(mazeFile);
        List<Room> rooms = maze.getMaze();
        List<Integer> expectedRoomNumbers = new ArrayList<>();
        for (int i = 0; i <= 25; i++) {
            expectedRoomNumbers.add(i);
        }
        List<Integer> actualRoomNumbers = new ArrayList<>();

        //Act
        for (Room room : rooms) {
            actualRoomNumbers.add((int) room.getRoomNumber());
        }

        //Assert
        System.out.println("TEST DATA: expectedRoomNumbers = " + expectedRoomNumbers.toString());
        System.out.println("TEST DATA: actualRoomNumbers = " + actualRoomNumbers.toString());
        assert actualRoomNumbers.equals(expectedRoomNumbers);

    }

    @Test
    void eachNonExitRoomShouldHaveAThreatOrATreasure() {

        //Arrange
        Maze maze = new Maze();
        maze.generateMaze(mazeFile);
        List<Room> rooms = maze.getMaze();

        //Act
        boolean allNonExitRoomsHaveContents = checkForContents(rooms);

        //Assert
        System.out.println("TEST DATA: Expected each non-exit room to have a contents");
        System.out.println("TEST DATA: Each non-exit room has a contents = " + allNonExitRoomsHaveContents);
        assert allNonExitRoomsHaveContents;
    }





    private boolean checkForContents(List<Room> rooms) {

        for (Room room : rooms) {
            if (room.getAvailablePassages().size() > 1 &&
                room.getContentsOfRoom() == null) {

                return false;
            }
        }
        return true;
    }


}
