package visualization;

import animal.Animal;
import animal.AnimalDNA;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import map.Field;
import simulation.DNAStatistics;
import simulation.Engine;

import java.util.Collection;

public class Statistics extends GridPane {
  private final Engine engine;
  private DNAStatistics dnaStatistics;
  private double energySum = 0;
  private double numberOfKids = 0;

  public Statistics(Engine engine, DNAStatistics dnaStatistics)
  {
    this.engine = engine;
    this.dnaStatistics = dnaStatistics;
  }

  public void updateStatistics(Animal trackedAnimal, int selectedAnimalCounter)
  {
    this.getChildren().clear();
    aliveAnimalsOperations();
    this.add(new Text("Day: " + engine.getDay()), 0, 0);
    this.add(new Text("Number of Animals: " + numberOfAnimals()), 0, 1);
    this.add(new Text("Alive Animals:" + engine.getAliveAnimals().size()), 0 , 2);
    this.add(new Text("Number of Plants: " + numberOfPlants()), 0, 3);
    this.add(new Text("Average Energy: " + energySum), 0, 4);
    this.add(new Text("Dominant DNA:"), 0 , 5);
    this.add(new Text(dominantGenotype()), 0 , 6);
    this.add(new Text("Animals with dominant DNA: " + dnaStatistics.getCounter()), 0 , 7);
    this.add(new Text("Average number of children: " + numberOfKids), 0 , 8);
    this.add(new Text("Average life length: " + averageLifeLength()), 0 , 9);
    this.add(new Text(""), 0 , 10);

    if(trackedAnimal != null)
    {
      this.add(new Text("Animal Status: " + selectedAnimalStatus(trackedAnimal)), 0, 11);
      this.add(new Text("Animal Energy: " + trackedAnimal.getEnergy()), 0, 12);
      this.add(new Text("Animal Genome:"), 0, 13);
      this.add(new Text(trackedAnimal.getDNA().toString()), 0, 14);
      this.add(new Text("Alive descendants: " + getSelectedAnimalAliveDescendants(selectedAnimalCounter)), 0, 15);
      this.add(new Text("Animal children: " + trackedAnimal.getNumberOfChildren()), 0, 16);
      this.add(new Text("Descendants: " + getSelectedAnimalDescendants(selectedAnimalCounter)), 0, 17);
    }
  }

  private int numberOfAnimals()
  {
    return engine.getAliveAnimals().size() + engine.getDeadAnimals().size();
  }

  private String selectedAnimalStatus(Animal animal)
  {
    if(animal.isDead())
      return "Dead " + animal.getDayOfDeath();
    return "Alive";
  }

  private int numberOfPlants()
  {
    int counter = 0;
    Collection<Field> colOfFields = engine.getFields().values();
    for (Field current : colOfFields)
    {
      if (current.getContainsGrass())
      {
        counter++;
      }
    }
    return counter;
  }

  private void aliveAnimalsOperations()
  {
    energySum = 0;
    numberOfKids = 0;
    for(Animal animal:engine.getAliveAnimals())
    {
      energySum += animal.getEnergy();
      numberOfKids += animal.getNumberOfChildren();
    }
    energySum = round(energySum, engine.getAliveAnimals().size());
    numberOfKids = round(numberOfKids, engine.getAliveAnimals().size());
  }

  private double round(double sum, int number)
  {
    return (double)((int)(sum/number*100))/100;
  }

  private int getSelectedAnimalAliveDescendants(int selectedAnimalCounter)
  {
    int counter = 0;
    for(Animal animal:engine.getAliveAnimals())
    {
      if(animal.getAncestryFactor() == selectedAnimalCounter && animal.getIsSelectedAnimal())
      {
        counter++;
      }
    }
    return counter;
  }

  private int getSelectedAnimalDescendants(int selectedAnimalCounter)
  {
    int counter = getSelectedAnimalAliveDescendants(selectedAnimalCounter);
    for(Animal animal:engine.getDeadAnimals())
    {
      if(animal.getAncestryFactor() == selectedAnimalCounter && animal.getIsSelectedAnimal())
      {
        counter++;
      }
    }
    return counter;
  }

  private double averageLifeLength()
  {
    double counter = 0;
    for(Animal animal:engine.getDeadAnimals())
    {
      counter += (animal.getDayOfDeath() - animal.getDayOfBirth());
    }
    return round(counter, engine.getDeadAnimals().size());
  }

  private String dominantGenotype()
  {
    AnimalDNA dna = dnaStatistics.getDominatingDNA();
    if(dna == null)
    {
      return "";
    }
    return dna.toString();
  }
}
