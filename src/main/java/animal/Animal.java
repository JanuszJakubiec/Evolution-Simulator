package animal;
import observer.*;
import vector2d.*;
import map.*;
import java.util.LinkedList;
import java.util.Objects;

public class Animal implements IPositionChangePublisher {
  private static int initialEnergy;
  private final WorldMap map;
  private Vector2d position;
  private int energy;
  private final int dayOfBirth;
  private int numberOfChildren = 0;
  private int dayOfDeath;
  private boolean isAlive = true;
  private final AnimalOrientation orientation = new AnimalOrientation();
  private final AnimalDNA DNA;
  private final LinkedList<IPositionChangeObserver> subscribers = new LinkedList<>();
  private Integer ancestryFactor = -1;
  private boolean isSelectedAnimal = false;

  public Animal(Animal parent1, Animal parent2, int dayOfBirth, Vector2d position)
  {
    ancestryFactor = Math.max(parent1.ancestryFactor, parent2.ancestryFactor);
    this.position = position;
    parent1.addChild();
    parent2.addChild();
    DNA = new AnimalDNA(parent1.DNA, parent2.DNA);
    map = parent1.map;
    this.map.place(this);
    this.dayOfBirth = dayOfBirth;
    energyHeritage(parent1, parent2);
  }

  public Animal(WorldMap map, int dayOfBirth, int initialEnergy, Vector2d position)
  {
    this.position = position;
    Animal.initialEnergy = initialEnergy;
    this.energy = initialEnergy;
    this.map = map;
    this.map.place(this);
    this.dayOfBirth = dayOfBirth;
    DNA = new AnimalDNA();
  }

  public void setAncestryFactor(int factor)
  {
    isSelectedAnimal = true;
    ancestryFactor = factor;
  }

  public boolean getIsSelectedAnimal()
  {
    return !isSelectedAnimal;
  }

  public int getAncestryFactor()
  {
    return ancestryFactor;
  }

  public boolean isDead()
  {
    return (!this.isAlive);
  }

  public boolean canMate()
  {
    return energy*2 >= initialEnergy;
  }

  public void ateGrass(int energy)
  {
    this.energy += energy;
  }

  public void takeEnergy(int energy, int day)
  {
    this.energy = this.energy - energy;
    if(this.energy <= 0)
    {
      this.dayOfDeath = day;
      this.isAlive = false;
    }
  }

  public static int getInitialEnergy()
  {
    return initialEnergy;
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
    if(isAlive)
      return -1;
    return dayOfDeath;
  }

  public AnimalDNA getDNA() {
    return DNA;
  }

  public Vector2d getPosition() {
    return position;
  }

  public void move()
  {
    orientation.rotate(DNA.decideRotation());
    Vector2d positionTMP = map.adjustPosition(position.add(orientation.step()));
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
      observer.positionChanged(oldPosition, newPosition, this);
    }
  }

  private void energyHeritage(Animal parent1, Animal parent2)
  {
    int energy_mod1 = parent1.energy%4;
    int energy_mod2 = parent2.energy%4;
    int energy1 = parent1.energy/4;
    int energy2 = parent2.energy/4;
    if((int)(Math.random()*2)==1)
      energy1 += energy_mod1;
    if((int)(Math.random()*2)==1)
      energy2 += energy_mod2;
    parent1.energy = parent1.energy - energy1;
    parent2.energy = parent2.energy - energy2;
    this.energy = energy1 + energy2;
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
