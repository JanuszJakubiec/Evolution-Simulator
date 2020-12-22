package simulation;

import animal.Animal;
import map.Field;
import map.WorldMap;
import vector2d.Vector2d;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Engine {
  private int day = 0;
  private final WorldMap map;
  private final int dayCost;
  private final int grassGain;

  public Engine(int width, int height, double jungleProportion, int nAnimals, int startAnimalEnergy, int dayCost, int grassGain)
  {
    map = new WorldMap(width, height, jungleProportion);
    this.grassGain = grassGain;
    this.dayCost = dayCost;
    placeNAnimalOnMap(nAnimals, startAnimalEnergy, width, height);
  }

  public Map<Vector2d, Field> getFields()
  {
    return map.getFields();
  }

  public int getDay()
  {
    return day;
  }

  public ArrayList<Animal> getAliveAnimals()
  {
    return map.getAliveAnimals();
  }

  public ArrayList<Animal> getDeadAnimals()
  {
    return map.getDeadAnimals();
  }

  public void nextDay()
  {
    map.cleanDeadAnimals();
    map.moveAllAnimals();
    map.takeEnergyFromAnimals(day, dayCost);
    map.makeAnimalsToEatGrass(grassGain);
    map.mateAnimals(day);
    map.plantGrass();
    day++;
  }

  private void placeNAnimalOnMap(int n, int startAnimalEnergy, int width, int height)
  {
    ArrayList<Vector2d> list = new ArrayList<>();
    for(int i =0; i<width;i++)
    {
      for(int j =0; j<height; j++)
      {
        list.add(new Vector2d(i,j));
      }
    }
    int i = 0;
    while(list.size() > 0 && i < n)
    {
      int pos = (int)(Math.random() * list.size());
      new Animal(map, day, startAnimalEnergy, list.get(pos));
      list.remove(pos);
      i++;
    }
  }
}
