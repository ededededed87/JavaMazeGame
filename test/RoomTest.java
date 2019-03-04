import org.junit.jupiter.api.Test;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;


class RoomTest {

//    private final Room room = new Room();

    @Test
    void shouldOnlyAllowCorrectlyFormattedPassageString(){

        //Arrange
        final List<String> passageStrings = new ArrayList<>();
        final List<Boolean> expectedResults = new ArrayList<>();
        final List<Boolean> actualResults = new ArrayList<>();

        passageStrings.add("");
        passageStrings.add("NSEW");
        passageStrings.add("SNWE");
        passageStrings.add("NSSE");
        passageStrings.add("nesw");

        expectedResults.add(false);
        expectedResults.add(true);
        expectedResults.add(true);
        expectedResults.add(false);
        expectedResults.add(true);

        //Act
        for (final String passageString : passageStrings) {

            actualResults.add(Room.isValidPassageString(passageString));
        }

        //Assert
        System.out.println("TEST DATA: expectedPassageStringValidity = " + expectedResults.toString());
        System.out.println("TEST DATA: actualPassageStringValidity = " + actualResults);
        assert(actualResults.equals(expectedResults));
    }


    @Test
    void shouldAddCorrectPassagesBasedOnValidString() {

        //Arrange
        Room room = new Room();
        String passageString = "NSE";
        List<String> actualPassages = new ArrayList<>();
        List<String> expectedPassages = new ArrayList<>();
        expectedPassages.add("North");
        expectedPassages.add("South");
        expectedPassages.add("East");


        //Act
        room.addPassages(passageString);

        //Assert

        for (int i = 0; i < passageString.length(); i++) {
            String passageDirection = String.valueOf(room.availablePassages.get(i).getDirection());
            actualPassages.add(passageDirection);

        }

        System.out.println("TEST DATA: expectedPassages = " + expectedPassages.toString());
        System.out.println("TEST DATA: actualPassages = " + actualPassages.toString());
        assert expectedPassages.equals(actualPassages);

        System.out.println("TEST DATA: expectedNumberOfPassages = " + passageString.length());
        System.out.println("TEST DATA: actualNumberOfPassages = " + room.availablePassages.size());
        assert (room.availablePassages.size() == passageString.length());
    }

    @Test
    void shouldGetCorrectDirectionFromLetter(){

        //Arrange
        char[] characters = {'N','E','S','W','X'};
        List<String> actualResults = new ArrayList<>();
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("North");
        expectedResults.add("East");
        expectedResults.add("South");
        expectedResults.add("West");
        expectedResults.add(null);


        //Act
        for (char character : characters) {
            actualResults.add(Room.getDirectionFromLetter(character));
        }

        //Assert
        System.out.println("TEST DATA: expectedDirectionsFromLetters = " + expectedResults.toString());
        System.out.println("TEST DATA: actualDirectionsFromLetters = " + actualResults.toString());
        assert expectedResults.equals(actualResults);
    }
}
