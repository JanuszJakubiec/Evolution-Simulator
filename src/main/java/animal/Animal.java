package animal;
import observer.*;
import vector2d.*;
import map.*;
import java.util.LinkedList;
import java.util.Objects;

public class Animal implements IPositionChangePublisher {
  private WorldMap map;
  private Vector2d position;
  private int energy;
  private final int dayOfBirth;
  private int numberOfChildren = 0;
  private int dayOfDeath;
  private boolean isAlive = true;
  private AnimalOrientation orientation = new AnimalOrientation();
  private AnimalDNA DNA;
  LinkedList<IPositionChangeObserver> subscribers = new LinkedList<IPositionChangeObserver>();

  public Animal(Animal parent1, Animal parent2, int dayOfBirth)
  {
    parent1.addChild();
    parent2.addChild();
    DNA = new AnimalDNA(parent1.DNA, parent2.DNA);
    map = parent1.map;
    this.dayOfBirth = dayOfBirth;
  }

  public Animal(WorldMap map, int dayOfBirth)
  {
    this.map = map;
    this.dayOfBirth = dayOfBirth;
    DNA = new AnimalDNA();
  }

  public void die(int dayOfDeath)
  {
    isAlive = false;
    this.dayOfDeath = dayOfDeath;
  }

  public void addChild()
  {
    numberOfChildren++;
  }

  public int getEnergy() {
    return energy;
  }

  public int getDayOfBirth() {
    return dayOfBirth;
  }

  public int getNumberOfChildren() {
    return numberOfChildren;
  }

  public int getDayOfDeath() {
    return dayOfDeath;
  }

  public AnimalDNA getDNA() {
    return DNA;
  }

  public Vector2d getPosition() {
    return position;
  }

  public MapDirection getOrientation() {
    return orientation.getOrientation();
  }

  public void move()
  {
    orientation.rotate(DNA.decideRotation());
    Vector2d positionTMP = position.add(orientation.step());
    positionChanged(position, positionTMP);
    position = positionTMP;
  }

  public String toString()
  {
    return orientation.toString();
  }

  public void addObserver(IPositionChangeObserver observer)
  {
    subscribers.add(observer);
  }

  public void removeObserver(IPositionChangeObserver observer)
  {
    subscribers.remove(observer);
  }

  private void positionChanged(Vector2d oldPosition, Vector2d newPosition)
  {
    for(IPositionChangeObserver observer : subscribers)
    {
      observer.positionChanged(oldPosition, newPosition);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Animal animal = (Animal) o;
    return energy == animal.energy &&
            dayOfBirth == animal.dayOfBirth &&
            numberOfChildren == animal.numberOfChildren &&
            dayOfDeath == animal.dayOfDeath &&
            Objects.equals(position, animal.position) &&
            Objects.equals(orientation, animal.orientation) &&
            Objects.equals(DNA, animal.DNA);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, energy, dayOfBirth, numberOfChildren, dayOfDeath, orientation, DNA);
  }
}
