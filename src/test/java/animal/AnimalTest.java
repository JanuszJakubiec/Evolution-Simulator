package animal;

import org.junit.jupiter.api.Test;
import map.WorldMap;
import vector2d.Vector2d;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {


  @Test
  void checkEnergyApplication() {
    Animal animal = new Animal(new WorldMap(5,5), 0, 5, new Vector2d(0,0));
    assertEquals(5,animal.getEnergy());
  }

  @Test
  void checkEnergyReduction() {
    Animal animal = new Animal(new WorldMap(5,5), 0, 5, new Vector2d(0,0));
    animal.newDay(3,1);
    assertEquals(2,animal.getEnergy());
  }

  @Test
  void checkDeathAction() {
    Animal animal = new Animal(new WorldMap(5,5), 0, 5, new Vector2d(0,0));
    animal.newDay(5,1);
    assertTrue(animal.isDead());
  }

  @Test
  void checkDayOfDeath() {
    Animal animal = new Animal(new WorldMap(5,5), 0, 5, new Vector2d(0,0));
    animal.newDay(5,1);
    assertEquals(1,animal.getDayOfDeath());
  }

  @Test
  void checkDayOfDeathIfAlive() {
    Animal animal = new Animal(new WorldMap(5,5), 6, 5, new Vector2d(0,0));
    assertEquals(-1,animal.getDayOfDeath());
  }

  @Test
  void checkIfAnimalCanMateTrue() {
    Animal animal = new Animal(new WorldMap(5,5), 0, 6, new Vector2d(0,0));
    animal.newDay(3,1);
    assertTrue(animal.canMate());
  }

  @Test
  void checkIfAnimalCanMateFalse() {
    Animal animal = new Animal(new WorldMap(5,5), 0, 5, new Vector2d(0,0));
    animal.newDay(3,1);
    assertFalse(animal.canMate());
  }

  @Test
  void checkIfAnimalEatsGrassProperly() {
    Animal animal = new Animal(new WorldMap(5,5), 0, 5, new Vector2d(0,0));
    animal.ateGrass(4);
    assertEquals(9, animal.getEnergy());
  }

  @Test
  void getDayOfBirth() {
    Animal animal = new Animal(new WorldMap(5,5), 5, 5, new Vector2d(0,0));
    assertEquals(5, animal.getDayOfBirth());
  }

  @Test
  void getNumberOfChildrenIf0() {
    Animal animal = new Animal(new WorldMap(5,5), 5, 5, new Vector2d(0,0));
    assertEquals(0, animal.getNumberOfChildren());
  }

  @Test
  void getNumberOfChildrenIfNot0() {
    Animal animal = new Animal(new WorldMap(5,5), 5, 5, new Vector2d(0,0));
    animal.addChild();
    assertEquals(1, animal.getNumberOfChildren());
  }

  @Test
  void checkIfAnimalDNAHasCorrectLength() {
    Animal animal = new Animal(new WorldMap(5,5), 5, 5, new Vector2d(0,0));
    assertEquals(32,animal.getDNA().getDNA().size());
  }

  @Test
  void checkIfAnimalDNAHasCorrectLengthAfterMating() {
    Animal parent1 = new Animal(new WorldMap(5,5), 5, 5, new Vector2d(0,0));
    Animal parent2 = new Animal(new WorldMap(5,5), 5, 5, new Vector2d(0,0));
    Animal animal = new Animal(parent1,parent2, 1, new Vector2d(0, 1));
    assertEquals(32,animal.getDNA().getDNA().size());
  }

  @Test
  void checkIfAnimalHasCorrectAmountOfEnergyAfterBirth() {
    Animal parent1 = new Animal(new WorldMap(5,5), 5, 8, new Vector2d(0,0));
    Animal parent2 = new Animal(new WorldMap(5,5), 5, 8, new Vector2d(0,0));
    Animal animal = new Animal(parent1,parent2, 1, new Vector2d(0, 1));
    assertEquals(4,animal.getEnergy());
  }

  @Test
  void checkIfAnimalHasInheritedInitialEnergyValueFromItsParentsTrue() {
    Animal parent1 = new Animal(new WorldMap(5,5), 5, 8, new Vector2d(0,0));
    Animal parent2 = new Animal(new WorldMap(5,5), 5, 8, new Vector2d(0,0));
    Animal animal = new Animal(parent1,parent2, 1, new Vector2d(0, 1));
    assertTrue(animal.canMate());
  }

  @Test
  void checkIfAnimalHasInheritedInitialEnergyValueFromItsParentsFalse() {
    Animal parent1 = new Animal(new WorldMap(5,5), 5, 8, new Vector2d(0,0));
    Animal parent2 = new Animal(new WorldMap(5,5), 5, 8, new Vector2d(0,0));
    Animal animal = new Animal(parent1,parent2, 1, new Vector2d(0, 1));
    animal.newDay(1,2);
    assertFalse(animal.canMate());
  }

  @Test
  void checkIfAnimalMovesProperly() {
    Animal animal = new Animal(new WorldMap(2,2), 5, 8, new Vector2d(0,0));
    animal.move();
    assertTrue(animal.getPosition().precedes(new Vector2d(1,1)) && animal.getPosition().follows(new Vector2d(0,0)));
    animal.move();
    assertTrue(animal.getPosition().precedes(new Vector2d(1,1)) && animal.getPosition().follows(new Vector2d(0,0)));
    animal.move();
    assertTrue(animal.getPosition().precedes(new Vector2d(1,1)) && animal.getPosition().follows(new Vector2d(0,0)));
    animal.move();
    assertTrue(animal.getPosition().precedes(new Vector2d(1,1)) && animal.getPosition().follows(new Vector2d(0,0)));
  }
}