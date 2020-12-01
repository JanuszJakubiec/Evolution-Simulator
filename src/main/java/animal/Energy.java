package animal;

public class Energy {
  private int energy;
  private int startEnergy;
  private Animal animal;

  public Energy(int startEnergy, Animal )
  {
    this.energy = energy;
    this.energy = startEnergy;
  }

  public Energy(Energy parent1, Energy parent2)
  {
    int energy_mod1 = parent1.energy%4;
    int energy_mod2 = parent2.energy%4;
    int energy1 = parent1.energy/4;
    int energy2 = parent2.energy/4;
    parent1.energy = parent1.energy - energy1;
    parent2.energy = parent2.energy - energy2;
    this.energy = energy1 + energy2;
  }

  public void addEnergy(int energy)
  {
    this.energy += energy;
  }

  public void survivedDay(int energy)
  {
    this.energy -= energy;
  }




}
