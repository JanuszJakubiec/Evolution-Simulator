package map;
import observer.IPositionChangeObserver;
import vector2d.*;
import animal.*;

import javax.swing.*;
import java.util.*;

public class WorldMap implements IPositionChangeObserver {
  private final Map<Vector2d, Field> animals = new LinkedHashMap<>();
  private final ArrayList<Animal> aliveAnimals = new ArrayList<>();
  private final ArrayList<Animal> deadAnimals = new ArrayList<>();
  private final Vector2d leftBottomCorner = new Vector2d(0,0);
  private final Vector2d rightTopCorner;

  public WorldMap(int width, int height)
  {
    rightTopCorner = new Vector2d(width-1,height-1);
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

  }

  public void eatGrass(int aEnergy)
  {
    Collection<Field> fields = animals.values();
    Iterator<Field> field = fields.iterator();
    while(field.hasNext())
    {
      Field current = field.next();
      if(current.containsAnimals())
      {
        current.eatGrass(aEnergy);
      }
    }
  }

  public void mateAnimals(int day)
  {
    Collection<Field> fields = animals.values();
    Iterator<Field> field = fields.iterator();
    while(field.hasNext())
    {
      Field current = field.next();
      if(current.containsAnimals())
      {
        current.mate(day, positionOfNewbornAnimal(current.getPosition()));
      }
    }
  }

  public void cleanDeadAnimals()
  {
    for(int i = 0; i<aliveAnimals.size(); i++)
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
    if (!animals.containsKey(position))
    {
      animals.put(position, new Field(position));
      //TODO delete position from available fields
    }
    animals.get(position).insertNewAnimal(animal);
  }

  public Vector2d adjustPosition(Vector2d position)
  {
    int x = position.x;
    int y = position.y;
    if(position.x > rightTopCorner.x)
      x = x - rightTopCorner.x;
    if(position.x < leftBottomCorner.x)
      x = x + rightTopCorner.x;
    if(position.y < leftBottomCorner.y)
      y = y + rightTopCorner.y;
    if(position.y > rightTopCorner.y)
      y = y - rightTopCorner.y;
    return new Vector2d(x,y);
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal)
  {
    animals.get(oldPosition).deleteAnimal(animal);
    if(animals.get(oldPosition).isEmpty())
    {
      animals.remove(oldPosition);
      //TODO add position to available fields
    }
    if (!animals.containsKey(newPosition))
    {
      animals.put(newPosition, new Field(newPosition));
      //TODO delete position from available fields
    }
    animals.get(newPosition).insertNewAnimal(animal);
  }

  private Vector2d positionOfNewbornAnimal(Vector2d parentPosition)
  {
    ArrayList<Vector2d> positions= new ArrayList<>();
    for(int i = -1;i<2;i++)
    {
      for(int j =-1;j<2;j++)
      {
        Vector2d tmp = new Vector2d(parentPosition.x + i, parentPosition.y + j);
        if(!animals.containsKey(tmp))
        {
          positions.add(tmp);
        }
        else
        {
          if(!animals.get(tmp).containsAnimals())
          {
            positions.add(tmp);
          }
        }
      }
    }
    if(positions.size() > 0)
    {
      return positions.get((int)(Math.random() * positions.size()));
    }
    else
    {
      int i = (int)(Math.random() * 3) - 1;
      int j = (int)(Math.random() * 3) - 1;
      return(new Vector2d(parentPosition.x + i, parentPosition.y+j));
    }
  }
}
