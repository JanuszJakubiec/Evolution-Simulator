package utilities;

import org.junit.jupiter.api.Test;
import vector2d.Vector2d;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JungleBorderCalculatorTest {

  @Test
  void checkIfJungleCalculatesCorrectly()
  {
    ArrayList<Vector2d> list = JungleBorderCalculator.calculate(1,1,0.5);
    assertTrue(list.get(2).equals(new Vector2d(0,0)) && list.get(3).equals(new Vector2d(-1,-1)));
  }

  @Test
  void checkIfJungleCalculatesCorrectlyTwo()
  {
    ArrayList<Vector2d> list = JungleBorderCalculator.calculate(2,2,0.5);
    System.out.print(list.get(2));
    System.out.print(list.get(3));
    assertTrue(list.get(2).equals(new Vector2d(0,0)) && list.get(3).equals(new Vector2d(0,0)));
  }

  @Test
  void checkIfJungleCalculatesCorrectlyThree()
  {
    ArrayList<Vector2d> list = JungleBorderCalculator.calculate(3,3,0.5);
    assertTrue(list.get(2).equals(new Vector2d(1,1)) && list.get(3).equals(new Vector2d(1,1)));
  }

  @Test
  void checkIfJungleCalculatesCorrectlyFour()
  {
    ArrayList<Vector2d> list = JungleBorderCalculator.calculate(4,4,0.5);
    assertTrue(list.get(2).equals(new Vector2d(1,1)) && list.get(3).equals(new Vector2d(2,2)));
  }

  @Test
  void checkIfJungleCalculatesCorrectlyTen()
  {
    ArrayList<Vector2d> list = JungleBorderCalculator.calculate(10,10,0.2);
    assertTrue(list.get(2).equals(new Vector2d(4,4)) && list.get(3).equals(new Vector2d(5,5)));
  }

  @Test
  void checkIfJungleCalculatesCorrectlyFifteen()
  {
    ArrayList<Vector2d> list = JungleBorderCalculator.calculate(15,15,0.3);
    assertTrue(list.get(2).equals(new Vector2d(5,5)) && list.get(3).equals(new Vector2d(8,8)));
  }

  @Test
  void checkIfJungleCalculatesCorrectlyTenFifteen()
  {
    ArrayList<Vector2d> list = JungleBorderCalculator.calculate(10,15,0.3);
    assertTrue(list.get(2).equals(new Vector2d(3,5)) && list.get(3).equals(new Vector2d(5,8)));
  }
}