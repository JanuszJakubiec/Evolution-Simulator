package simulation;

import animal.Animal;
import animal.AnimalDNA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DNAStatistics {
  private final Engine engine;
  private Map<AnimalDNA, Integer> activeGenotype;
  private AnimalDNA dominatingDNA = null;
  private int counter = 0;
  public DNAStatistics(Engine engine)
  {
    this.engine = engine;
  }

  public AnimalDNA getDominatingDNA() {
    return dominatingDNA;
  }

  public int getCounter() {
    return counter;
  }

  public void update()
  {
    dominatingDNA = null;
    activeGenotype = new HashMap<>();
    counter = 0;
    ArrayList<Animal> animals = engine.getAliveAnimals();
    for(Animal animal:animals)
    {
      if(activeGenotype.containsKey(animal.getDNA()))
      {
        Integer value = activeGenotype.get(animal.getDNA());
        activeGenotype.remove(animal.getDNA());
        value = value + 1;
        if(counter < value)
        {
          counter = value;
          dominatingDNA = animal.getDNA();
        }
        activeGenotype.put(animal.getDNA(), value);
      }
      else
      {
        activeGenotype.put(animal.getDNA(), 1);
        if(1>counter)
        {
          counter = 1;
          dominatingDNA = animal.getDNA();
        }
      }
    }
  }
}
