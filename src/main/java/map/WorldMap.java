package map;
import observer.IFieldAvailabilityObserver;
import observer.IPositionChangeObserver;
import observer.IFieldAvailabilityPublisher;
import vector2d.*;
import animal.*;

import java.util.*;

public class WorldMap implements IPositionChangeObserver, IFieldAvailabilityPublisher {
  private final Map<Vector2d, Field> fields = new LinkedHashMap<>();
  private final ArrayList<Animal> aliveAnimals = new ArrayList<>();
  private final ArrayList<Animal> deadAnimals = new ArrayList<>();
  private final Vector2d leftBottomCorner = new Vector2d(0,0);
  private final Vector2d rightTopCorner;
  private GrassSectors sectors;
  LinkedList<IFieldAvailabilityObserver> subscribers = new LinkedList<>();

  public WorldMap(int width, int height, double jungleRatio)
  {
    rightTopCorner = new Vector2d(width-1, height-1);
    sectors = new GrassSectors(1, calculateSectorBorders(width, height, jungleRatio) , this);
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
      animal.takeEnergy(energy,day);
  }

  public void plantGrass()
  {
    sectors.plantGrass(fields);
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
        Animal animal = aliveAnimals.get(i);
        fields.get(animal.getPosition()).deleteAnimal(aliveAnimals.get(i));
        if(fields.get(animal.getPosition()).isEmpty())
        {
          fields.remove(animal.getPosition());
          newFreePosition(animal.getPosition());
        }
        aliveAnimals.remove(i);
        i--;
      }
    }
  }

  public void place(Animal animal)
  {
    Vector2d position = animal.getPosition();
    aliveAnimals.add(animal);
    animal.addObserver(this);
    if (!fields.containsKey(position))
    {
      fields.put(position, new Field(position));
      makePositionUnavailable(position);
    }
    fields.get(position).insertNewAnimal(animal);
  }

  public Vector2d adjustPosition(Vector2d position)
  {
    int x = position.x;
    int y = position.y;
    if(position.x > rightTopCorner.x)
      x = x - rightTopCorner.x - 1;
    if(position.x < leftBottomCorner.x)
      x = x + rightTopCorner.x;
    if(position.y < leftBottomCorner.y)
      y = y + rightTopCorner.y;
    if(position.y > rightTopCorner.y)
      y = y - rightTopCorner.y-1;
    return new Vector2d(x,y);
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal)
  {
    fields.get(oldPosition).deleteAnimal(animal);
    if(fields.get(oldPosition).isEmpty())
    {
      fields.remove(oldPosition);
      newFreePosition(oldPosition);
    }
    if (!fields.containsKey(newPosition))
    {
      fields.put(newPosition, new Field(newPosition));
      makePositionUnavailable(newPosition);
    }
    fields.get(newPosition).insertNewAnimal(animal);
  }

  @Override
  public void addObserver(IFieldAvailabilityObserver observer)
  {
    subscribers.add(observer);
  }

  @Override
  public void removeObserver(IFieldAvailabilityObserver observer)
  {
    subscribers.remove(observer);
  }

  private void newFreePosition(Vector2d position)
  {
    for(IFieldAvailabilityObserver observer:subscribers)
    {
      observer.setPositionAsAvailable(position);
    }
  }

  private void makePositionUnavailable(Vector2d position)
  {
    for(IFieldAvailabilityObserver observer:subscribers)
    {
      observer.setPositionAsUnavailable(position);
    }
  }

  private ArrayList<Vector2d> calculateSectorBorders(int width, int height, double ratio)
  {
    ArrayList<Vector2d> list = new ArrayList<>();
    list.add(new Vector2d(0,0));
    list.add(new Vector2d(width-1, height-1));
    int x = (int) ((int) width*ratio);
    int y = (int) ((int) height*ratio);
    int dx = (width - x)/2;
    int dy = (height - y)/2;
    list.add(new Vector2d(dx,dy));
    list.add(new Vector2d(dx+x -1, dy+y-1));
    return list;
  }
}
