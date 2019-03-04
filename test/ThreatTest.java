import org.junit.jupiter.api.Test;

public class ThreatTest {

    Threat skeleton = new Threat("skeleton");
    Threat dragon = new Threat("dragon");

    @Test
    void shouldGetCorrectWeaponForThreatType() {

        //Arrange
        String expectedWeaponForSkeleton = "club";
        String expectedWeaponForDragon = "bow";

        //Act
        String actualResultForSkeleton = Threat.getWeapon(skeleton.getThreatType());
        String actualResultForDragon = Threat.getWeapon(dragon.getThreatType());
        String actualResultForInvalidThreatType = Threat.getWeapon("invalid threat");

        //Assert
        System.out.println("TEST DATA: expectedWeaponForSkeleton = " + expectedWeaponForSkeleton);
        System.out.println("TEST DATA: actualWeaponForSkeleton = " + actualResultForSkeleton);
        System.out.println("TEST DATA: expectedWeaponForDragon = " + expectedWeaponForDragon);
        System.out.println("TEST DATA: actualWeaponForDragon = " + actualResultForDragon);
        assert expectedWeaponForSkeleton.equals(actualResultForSkeleton);
        assert expectedWeaponForDragon.equals(actualResultForDragon);
        assert actualResultForInvalidThreatType == null;

    }

    @Test
    void shouldGetCorrectDefeatMessageForThreatType() {

        //Arrange
        String expectedMessageForSkeleton = "You hit the skeleton with your club and the skeleton crumbles to dust.";
        String expectedMessageForDragon = "You hit the dragon with an arrow and the dragon explodes in a ball of flame.";

        //Act
        String actualResultForSkeleton = Threat.getSuccessMessage(skeleton.getThreatType());
        String actualResultForDragon = Threat.getSuccessMessage(dragon.getThreatType());
        String actualResultForInvalidThreatType = Threat.getSuccessMessage("invalid threat");

        //Assert
        System.out.println("TEST DATA: expectedSuccessMessageForSkeleton = " + expectedMessageForSkeleton);
        System.out.println("TEST DATA: actualSuccessMessageForSkeleton = " + actualResultForSkeleton);
        System.out.println("TEST DATA: expectedSuccessMessageForDragon = " + expectedMessageForDragon);
        System.out.println("TEST DATA: actualSuccessMessageForDragon = " + actualResultForDragon);
        assert expectedMessageForSkeleton.equals(actualResultForSkeleton);
        assert expectedMessageForDragon.equals(actualResultForDragon);
        assert actualResultForInvalidThreatType == null;

    }

    @Test
    void shouldGetCorrectFailureMessageWhenUsingIncorrectWeapon() {
        //Arrange
        String expectedMessageForSkeleton = "You shoot your bow at the skeleton but the arrow flies straight through it.";
        String expectedMessageForDragon = "You swing your club at the dragon with all your might. Unfortunately, the dragon is flying and you miss by a long way.";

        //Act
        String actualResultForSkeleton = Threat.getFailureMessage(skeleton.getThreatType());
        String actualResultForDragon = Threat.getFailureMessage(dragon.getThreatType());
        String actualResultForInvalidThreatType = Threat.getFailureMessage("invalid threat");

        //Assert
        System.out.println("TEST DATA: expectedFailureMessageForSkeleton = " + expectedMessageForSkeleton);
        System.out.println("TEST DATA: actualFailureMessageForSkeleton = " + actualResultForSkeleton);
        System.out.println("TEST DATA: expectedFailureMessageForDragon = " + expectedMessageForDragon);
        System.out.println("TEST DATA: actualFailureMessageForDragon = " + actualResultForDragon);
        assert expectedMessageForSkeleton.equals(actualResultForSkeleton);
        assert expectedMessageForDragon.equals(actualResultForDragon);
        assert actualResultForInvalidThreatType == null;
    }



}
