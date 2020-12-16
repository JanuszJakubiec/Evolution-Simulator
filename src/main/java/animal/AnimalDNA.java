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

  private void decideWhichGenesMultiply(ArrayList<Integer> DNAa, ArrayList<Integer> DNAb, int first, int second)
  {
    int l1 = (int)(Math.random() * 2);
    int l2 = (int)(Math.random() * 2);
    int l3;
    if(l2==l1)
      l3 = 1-l1; // if l1 == 1, l3 = 1-1 = 0, if l1 = 0, l3 = 1-0 = 1, its always different value than l1
    else
      l3 = (int)(Math.random() * 2);

    if(l1 == 0) {
      insertValues(DNAa, 0, first);
    }
    else
      insertValues(DNAb, 0, first);

    if(l2 == 0) {
      insertValues(DNAa, first, second);
    }
    else
      insertValues(DNAb, first, second);

    if(l3 == 0) {
      insertValues(DNAa, second, 32);
    }
    else
      insertValues(DNAb, second, 32);
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
}