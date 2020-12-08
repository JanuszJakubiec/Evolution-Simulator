package map;
import observer.IPositionChangeObserver;
import vector2d.*;
import animal.*;

import java.util.*;

public class WorldMap implements IPositionChangeObserver {
  private final Map<Vector2d, Field> fields = new LinkedHashMap<>();
  private final ArrayList<Animal> aliveAnimals = new ArrayList<>();
  private final ArrayList<Animal> deadAnimals = new ArrayList<>();
  private final Vector2d leftBottomCorner = new Vector2d(0,0);
  private final Vector2d rightTopCorner;

  public WorldMap(int width, int height)
  {
    rightTopCorner = new Vector2d(width-1,height-1);
  }

  public ArrayList<Animal> getDeadAnimals()
  {
    return deadAnimals;
  }

  public ArrayList<Animal> getAliveAnimals()
  {
    return aliveAnimals;
  }

  public Map<Vector2d, Field> getFields()
  {
    return fields;
  }

  public void moveAllAnimals()
  {
    for(Animal animal:aliveAnimals)
      animal.move();
  }

  public void takeEnergyFromAnimals(int day, int energy)
  {
    for(Animal animal:aliveAnimals)
      animal.newDay(day, energy);
  }

  public void plantGrass()
  {
    //TODO
  }

  public void makeAnimalsToEatGrass(int aEnergy)
  {
    Collection<Field> colOfFields = fields.values();
    for (Field current : colOfFields)
    {
      if (current.containsAnimals())
      {
        current.eatGrass(aEnergy);
      }
    }
  }

  public void mateAnimals(int day)
  {
    MatingAssistant matingOperator = new MatingAssistant(fields, day);
    matingOperator.startMating();
  }

  public void cleanDeadAnimals()
  {
    for(int i = 0; i < aliveAnimals.size(); i++)
    {
      if(aliveAnimals.get(i).isDead())
      {
        deadAnimals.add(aliveAnimals.get(i));
        aliveAnimals.remove(i);
        i--;
      }
    }
  }

  public void place(Animal animal)
  {
    Vector2d position = animal.getPosition();
    animal.addObserver(this);
    if (!fields.containsKey(position))
    {
      fields.put(position, new Field(position));
      //TODO delete position from available fields
    }
    fields.get(position).insertNewAnimal(animal);
  }

  public Vector2d adjustPosition(Vector2d position)
  {
    int x = position.x;
    int y = position.y;
    if(position.x >= rightTopCorner.x)
      x = x - rightTopCorner.x;
    if(position.x < leftBottomCorner.x)
      x = x + rightTopCorner.x;
    if(position.y < leftBottomCorner.y)
      y = y + rightTopCorner.y;
    if(position.y >= rightTopCorner.y)
      y = y - rightTopCorner.y;
    return new Vector2d(x,y);
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal)
  {
    fields.get(oldPosition).deleteAnimal(animal);
    if(fields.get(oldPosition).isEmpty())
    {
      fields.remove(oldPosition);
      //TODO add position to available fields
    }
    if (!fields.containsKey(newPosition))
    {
      fields.put(newPosition, new Field(newPosition));
      //TODO delete position from available fields
    }
    fields.get(newPosition).insertNewAnimal(animal);
  }
}
