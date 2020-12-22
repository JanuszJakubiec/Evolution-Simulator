package animal;

import java.util.*;

public class AnimalDNA {
  ArrayList<Integer> DNA = new ArrayList<Integer>();

  public AnimalDNA(AnimalDNA parent1, AnimalDNA parent2)
  {
    int first = (int)(Math.random() * (31-1 + 1)) + 1;
    int second = first;
    while(second == first)
      second = (int)(Math.random() * (31-1+1)) + 1;
    if(first > second)
    {
      int tmp = first;
      first = second;
      second = tmp;
    }
    decideWhichGenesMultiply(parent1.getDNA(), parent2.getDNA(), first, second);
    fixResult();
  }

  public AnimalDNA()
  {
    generateDNA();
  }

  public int decideRotation()
  {
    return DNA.get((int)(Math.random() * 32));
  }

  public ArrayList<Integer> getDNA() {
    return DNA;
  }

  private void fixResult() {
    int[] counter = new int[8];
    for (int i = 0; i < 8; i++) {
      counter[i] = 0;
    }
    int len = 32;
    for (int i = 0; i < len; i++) {
      int j = DNA.get(i);
      if (counter[j] == 0) {
        DNA.remove(i);
        i--;
        len--;
      }
      counter[j]++;
    }
    int deficits = 0;
    for (int i = 0; i < 8; i++) {
      if (counter[i] == 0) {
        deficits++;
      }
    }
    for (int i = 0; i < deficits; i++)
    {
      DNA.remove((int)(Math.random() * DNA.size()));
    }
    for(int i=0; i<8; i++)
    {
      DNA.add(i);
    }
    Collections.sort(DNA);
  }

  private void decideWhichGenesMultiply(ArrayList<Integer> parent1DNA, ArrayList<Integer> parent2DNA, int first, int second)
  {
    //parent1, parent2 and parent3 defines from which parent comes part 1, part 2 and part 3 of the DNA
    int parent1 = (int)(Math.random() * 2);
    int parent2 = (int)(Math.random() * 2);
    int parent3;
    if(parent2==parent1)
      parent3 = 1-parent1; // if l1 == 1, l3 = 1-1 = 0, if l1 = 0, l3 = 1-0 = 1, its always different value than l1
    else
      parent3 = (int)(Math.random() * 2);

    if(parent1 == 0) {
      insertValues(parent1DNA, 0, first);
    }
    else
      insertValues(parent2DNA, 0, first);

    if(parent2 == 0) {
      insertValues(parent1DNA, first, second);
    }
    else
      insertValues(parent2DNA, first, second);

    if(parent3 == 0) {
      insertValues(parent1DNA, second, 32);
    }
    else
      insertValues(parent2DNA, second, 32);
  }


  private void insertValues(ArrayList<Integer> source, int start, int end)
  {
    for(int i=start; i<end; i++)
      DNA.add(source.get(i));
  }

  private void generateDNA()
  {
    for(int i=0; i<8; i++)
    {
      DNA.add(i);
    }
    for(int i=8 ; i<32 ; i++)
    {
      DNA.add((int)(Math.random() * 8));
    }
    Collections.sort(DNA);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AnimalDNA animalDNA = (AnimalDNA) o;
    return Objects.equals(DNA, animalDNA.DNA);
  }

  @Override
  public int hashCode() {
    return Objects.hash(DNA);
  }

  @Override
  public String toString()
  {
    String stringDNA = "";
    for(int i:DNA)
    {
      stringDNA += Integer.toString(i);
    }
    return stringDNA;
  }
}