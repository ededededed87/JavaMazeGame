import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TreasureTest {

    @Test
    void shouldAssignValueCorrectValueToTreasure() {

        //Arrange
        Treasure silverCoin = new Treasure("silver coin");
        Treasure goldCoin = new Treasure("gold coin");
        Treasure diamond = new Treasure("diamond");
        Treasure chest = new Treasure("chest");

        List<Integer> expectedValues = new ArrayList<>();
        expectedValues.add(5);
        expectedValues.add(10);
        expectedValues.add(25);
        expectedValues.add(50);

        List<Integer> actualValues = new ArrayList<>();

        //Act
        actualValues.add(silverCoin .getValue());
        actualValues.add(goldCoin .getValue());
        actualValues.add(diamond .getValue());
        actualValues.add(chest .getValue());

        //Assert
        System.out.println("TEST DATA: expectedTreasureValues = " + expectedValues.toString());
        System.out.println("TEST DATA: actualTreasureValues = " + actualValues.toString());
        assert expectedValues.equals(actualValues);
    }
}
