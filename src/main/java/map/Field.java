package map;

import animal.Animal;
import vector2d.Vector2d;

import java.util.ArrayList;
import java.util.LinkedList;

public class Field {
  private Vector2d position;
  private ArrayList<Animal> animals = new ArrayList<Animal>();
  private boolean containsGrass = false;
  public Field(Vector2d position)
  {
    this.position = position;
  }

  public void plantGrass()
  {
    containsGrass = true;
  }

  public boolean doesContainsGrass()
  {
    return containsGrass;
  }

  public Vector2d getPosition() {
    return position;
  }

  public boolean isEmpty()
  {
    if(animals.size() == 0 && !containsGrass)
      return true;
    return false;
  }

  public boolean containsAnimals()
  {
    return animals.size() != 0;
  }

  public void eatGrass(int aEnergy) {
    if(containsGrass)
    {
      ArrayList<Animal> strongestAnimals = animalsWithTheMostEnergy();
      int rest = aEnergy%strongestAnimals.size();
      int size = strongestAnimals.size();
      int rand = (int)(Math.random() * size);
      int additionalEnergy = aEnergy/size;
      for(int i = 0; i<size; i++)
      {
        strongestAnimals.get(i).ateGrass(additionalEnergy);
        if(i == rand)
          strongestAnimals.get(i).ateGrass(rest);
      }
    }
    containsGrass = false;
  }

  public void mate(int day, Vector2d position)
  {
    if(animals.size()>1)
    {
      ArrayList<Animal> strongestAnimals = TwoAnimalsWithTheMostEnergy();
      if(strongestAnimals.get(1).canMate())
      {
        new Animal(strongestAnimals.get(0), strongestAnimals.get(1), day, position);
      }
    }
  }

  public void insertNewAnimal(Animal animal)
  {
    int i;
    for(i = 0;i<animals.size();i++)
    {
      if (animals.get(i).getEnergy() <= animal.getEnergy())
      {
        break;
      }
    }
    animals.add(i,animal);
  }

  public void deleteAnimal(Animal animal)
  {
    int i;
    for(i = 0;i<animals.size();i++)
    {
      if (animals.get(i) == animal)
      {
        break;
      }
    }
    animals.remove(i);
  }

  private ArrayList<Animal> animalsWithTheMostEnergy()
  {
    ArrayList<Animal> animalsEnergyLVL = new ArrayList<Animal>();
    if (animals.size() > 0) {
      int energy = 0;
      energy = animals.get(0).getEnergy();
      animalsEnergyLVL.add(animals.get(0));
      if (animals.size() > 1 && animals.get(1).getEnergy() == energy)
      {
        int i = 2;
        while (i < animals.size() && animals.get(i).getEnergy() == energy)
        {
          animalsEnergyLVL.add(animals.get(i));
          i++;
        }
      }
    }
    return animalsEnergyLVL;
  }

  private ArrayList<Animal> twoSameLevelAnimals(ArrayList<Animal> list)
  {
    int first = (int)(Math.random() * list.size());
    int second = first;
    int i = 0;
    while(second==first && i<5)
    {
      second = (int)(Math.random() * list.size());
      i++;
    }
    if(first == second)
    {
      first = 0;
      second = 1;
    }
    ArrayList<Animal> array = new ArrayList<>();
    array.add(list.get(first));
    array.add(list.get(second));
    return array;
  }

  private ArrayList<Animal> twoDifferentLevelAnimals(ArrayList<Animal> list)
  {
    ArrayList<Animal> array = new ArrayList<>();
    array.add(list.get(0));
    int second = (int)(Math.random() * (list.size()-1)) + 1;
    array.add(list.get(second));
    return array;
  }

  private ArrayList<Animal> TwoAnimalsWithTheMostEnergy()
  {
    ArrayList<Animal> animalsEnergyLVL = animalsWithTheMostEnergy();
    if(animalsEnergyLVL.size() > 1)
      return twoSameLevelAnimals(animalsEnergyLVL);
    animalsEnergyLVL.add(animals.get(1));
    int energy = animals.get(1).getEnergy();
    int i = 2;
    while(i<animals.size() && animals.get(i).getEnergy()==energy)
      animalsEnergyLVL.add(animals.get(i));
    return twoDifferentLevelAnimals(animalsEnergyLVL);
  }
}
