package map;

import animal.Animal;
import org.junit.jupiter.api.Test;
import vector2d.Vector2d;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {

  @Test
  void checkIfAliveAnimalsArePutIntoAliveAnimalsArray()
  {
    WorldMap map = new WorldMap(5,5, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(4,4));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    assertEquals(2, map.getAliveAnimals().size());
  }

  @Test
  void checkIfMapTakesEnergyFromAllAliveAnimals()
  {
    WorldMap map = new WorldMap(5,5, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(4,4));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.takeEnergyFromAnimals(3,8);
    assertEquals(0, animal1.getEnergy());
  }

  @Test
  void checkIfDeadAnimalsArePutIntoDeadAnimalsArray()
  {
    WorldMap map = new WorldMap(5,5, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(4,4));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.takeEnergyFromAnimals(3,8);
    map.cleanDeadAnimals();
    assertEquals(2, map.getDeadAnimals().size());
  }

  @Test
  void checkIfAnimalsPositionIsAdjustedWhenOutOfTheBorder()
  {
    WorldMap map = new WorldMap(3,3, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(2,2));
    map.moveAllAnimals();
    assertTrue(animal1.getPosition().precedes(new Vector2d(2,2)) && animal1.getPosition().follows(new Vector2d(0,0)));
    map.moveAllAnimals();
    assertTrue(animal1.getPosition().precedes(new Vector2d(2,2)) && animal1.getPosition().follows(new Vector2d(0,0)));
    map.moveAllAnimals();
    assertTrue(animal1.getPosition().precedes(new Vector2d(2,2)) && animal1.getPosition().follows(new Vector2d(0,0)));
    map.moveAllAnimals();
    assertTrue(animal1.getPosition().precedes(new Vector2d(2,2)) && animal1.getPosition().follows(new Vector2d(0,0)));
    map.moveAllAnimals();
    assertTrue(animal1.getPosition().precedes(new Vector2d(2,2)) && animal1.getPosition().follows(new Vector2d(0,0)));
    map.moveAllAnimals();
    assertTrue(animal1.getPosition().precedes(new Vector2d(2,2)) && animal1.getPosition().follows(new Vector2d(0,0)));
  }

  @Test
  void checkIfGrassIsPlanted()
  {
    WorldMap map = new WorldMap(1,1, 0);
    map.plantGrass();
    assertFalse(map.getFields().get(new Vector2d(0,0)).isEmpty());
  }

  @Test
  void checkIfAllAnimalsEatsGrass()
  {
    WorldMap map = new WorldMap(1,1, 0);
    map.plantGrass();
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    map.makeAnimalsToEatGrass(4);
    assertEquals(12, animal1.getEnergy());
  }

  @Test
  void checkIfGrassDisappearsAfterBeingEaten()
  {
    WorldMap map = new WorldMap(1,1, 0);
    map.plantGrass();
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    map.makeAnimalsToEatGrass(4);
    map.makeAnimalsToEatGrass(4);
    assertEquals(12, animal1.getEnergy());
  }

}