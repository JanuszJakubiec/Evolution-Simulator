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

  @Test
  void testIfMatingCreatesNewAnimal()
  {
    WorldMap map = new WorldMap(5,5, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.mateAnimals(3);
    assertEquals(3, map.getAliveAnimals().size());
  }

  @Test
  void testIfAfterMatingAnimalHasProperAmountOfEnergy()
  {
    WorldMap map = new WorldMap(5,5, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.mateAnimals(3);
    assertEquals(4, map.getAliveAnimals().get(2).getEnergy());
  }

  @Test
  void testIfAfterMatingParentHasProperAmountOfEnergy()
  {
    WorldMap map = new WorldMap(5,5, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.mateAnimals(3);
    assertEquals(6, animal1.getEnergy());
  }

  @Test
  void testIfAfterMatingAnimalIsInFreePosition()
  {
    WorldMap map = new WorldMap(5,5, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.mateAnimals(3);
    assertNotEquals(animal1.getPosition(), map.getAliveAnimals().get(2).getPosition());
  }

  @Test
  void testIfAfterMatingAnimalIsInCorrectPosition()
  {
    WorldMap map = new WorldMap(1,1, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.mateAnimals(3);
    assertEquals(animal1.getPosition(), map.getAliveAnimals().get(2).getPosition());
  }

  @Test
  void testIfCorrectAnimalsAreMating()
  {
    WorldMap map = new WorldMap(1,1, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.mateAnimals(3);
    map.mateAnimals(4);
    int energy = map.getAliveAnimals().get(3).getEnergy();
    assertTrue(energy == 2 || energy == 4 || energy == 6);
  }

  @Test
  void testIfCorrectAnimalsAreMatingTwo()
  {
    WorldMap map = new WorldMap(1,1, 0.5);
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    map.mateAnimals(3);
    map.mateAnimals(4);
    int energy1 = map.getAliveAnimals().get(3).getEnergy();
    map.mateAnimals(5);
    int energy2 = map.getAliveAnimals().get(4).getEnergy();
    if(energy1 == 2)
    {
      assertTrue((energy2 == 2 || energy2 == 3 || energy2 == 4) && getEnergySum(map) == 16);
    }
    if(energy1 == 4)
    {
      assertTrue((energy2 == 2 || energy2 == 3) && getEnergySum(map) == 16);
    }
    if(energy1 == 6)
    {
      assertTrue((energy2 == 2 || energy2 == 4) && getEnergySum(map) == 16);
    }
  }

  @Test
  void checkIfProperAnimalEatsTheGrass()
  {
    WorldMap map = new WorldMap(1,1, 0.5);
    map.plantGrass();
    Animal animal1 = new Animal(map,0,8,new Vector2d(0,0));
    Animal animal2 = new Animal(map,0,8,new Vector2d(0,0));
    animal2.takeEnergy(2, 0);
    map.mateAnimals(1);
    map.makeAnimalsToEatGrass(4);
    assertEquals(10, animal1.getEnergy());
  }

  @Test
  void checkIfGrassIsSpawningCorrectlyNumbers()
  {
    WorldMap map = new WorldMap(2,2, 0.5);
    map.plantGrass();
    assertEquals(map.getFields().size(), 2);
  }

  @Test
  void checkIfGrassIsSpawningCorrectlyPositions()
  {
    WorldMap map = new WorldMap(2,2, 0.5);
    map.plantGrass();
    assertFalse(map.getFields().get(new Vector2d(0,0)).isEmpty());
  }


  private int getEnergySum(WorldMap map)
  {
    int sum = 0;
    for(Animal animal : map.getAliveAnimals())
    {
      sum += animal.getEnergy();
    }
    return sum;
  }
}