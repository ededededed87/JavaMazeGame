import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {

    Maze() {
    }

    List<Room> getMaze() {
        return maze;
    }

    private final static List<Room> maze = new ArrayList<>();

    void generateMaze(String mazeFile) {

        maze.clear();


        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(new FileReader(mazeFile));

            JSONObject roomList = (JSONObject) object.get("rooms");

            for (int i = 0; i < roomList.size(); i++) {
                String roomIdentifier = "room" + i;
                JSONObject room = (JSONObject) roomList.get(roomIdentifier);
                String passageString = (String) room.get("passages");
                Long roomNumber = (Long) room.get("roomNumber");
                boolean canStartHere = Boolean.valueOf((String) room.get("canStartHere"));

                String contentsType = (String) room.get("contents");
                if (contentsType.equals("threat")) {
                    String threatType = (String) room.get("threatType");
                    RoomContents roomContents = new Threat(threatType);
                    maze.add(i, new Room(roomContents, passageString, roomNumber, canStartHere));
                }
                else if (contentsType.equals("treasure")) {
                    String treasureType = (String) room.get("treasureType");
                    Treasure roomContents = new Treasure(treasureType);
                    maze.add(i, new Room(roomContents, passageString, roomNumber, canStartHere));
                }
                else {
                    maze.add(i, new Room(null, passageString, roomNumber, canStartHere));
                }
            }
        }
        catch (IOException | ParseException e) {
            System.out.println("Maze could not be generated. Please check if configuration file is valid.");
            System.out.println("A valid maze configuration file should be located at " + mazeFile);
            System.out.println(e.getMessage());

        }
    }
}


