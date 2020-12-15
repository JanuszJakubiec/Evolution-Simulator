package animal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalOrientationTest {

  @Test
  void checkOrientation()
  {
    AnimalOrientation orientation = orientationMakeNorth();
    assertEquals(MapDirection.NORTH, orientation.getOrientation());
  }

  @Test
  void checkRotation()
  {
    AnimalOrientation orientation = orientationMakeNorth();
    orientation.rotate(5);
    assertEquals(MapDirection.SOUTHWEST, orientation.getOrientation());
  }

  @Test
  void checkToString()
  {
    AnimalOrientation orientation = orientationMakeNorth();
    String check = "";
    for(int i = 0; i< 8;i++)
    {
      orientation.rotate(1);
      check += orientation.toString();
    }
    assertEquals("12345670", check);
  }

  private AnimalOrientation orientationMakeNorth()
  {
    AnimalOrientation orientation = new AnimalOrientation();
    int n = Integer.parseInt(orientation.toString());
    orientation.rotate(8-n);
    return orientation;
  }
}