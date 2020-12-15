package animal;

import animal.AnimalDNA;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class AnimalDNATest {

  @Test
  void checkIfDNAGeneratesDNAWithCorrectLength()
  {
    AnimalDNA DNA = new AnimalDNA();
    assertEquals(32, DNA.getDNA().size());
  }

  @Test
  void checkIfDNAGeneratesDNAWithEveryNeededValues()
  {
    AnimalDNA DNA = new AnimalDNA();
    assertTrue(checkValues(DNA.getDNA()));
  }

  @Test
  void checkIfDNACreatedWithConstructorWith2DNAsInputCreatesProperly()
  {
    AnimalDNA DNA1 = new AnimalDNA();
    AnimalDNA DNA2 = new AnimalDNA();
    AnimalDNA DNA = new AnimalDNA(DNA1, DNA2);
    assertEquals(32, DNA.getDNA().size());
  }

  @Test
  void checkIfDNACreatedWithConstructorHasEveryNeededValues()
  {
    AnimalDNA DNA1 = new AnimalDNA();
    AnimalDNA DNA2 = new AnimalDNA();
    AnimalDNA DNA = new AnimalDNA(DNA1, DNA2);
    assertTrue(checkValues(DNA.getDNA()));
  }

  @Test
  void checkIfRandomRotationWorks()
  {
    AnimalDNA DNA = new AnimalDNA();
    int rotation = DNA.decideRotation();
    assertTrue(rotation >=0 && rotation <= 7);
  }


  private boolean checkValues(ArrayList<Integer> DNA)
  {
    int[] counter = new int[8];
    for (int i = 0; i < 8; i++)
    {
      counter[i] = 0;
    }
    for (int i = 0; i < 32; i++)
    {
      counter[DNA.get(i)]++;
    }
    for (int i = 0; i < 8; i++)
    {
      if(counter[i] == 0)
        return false;
    }
    return true;
  }
}