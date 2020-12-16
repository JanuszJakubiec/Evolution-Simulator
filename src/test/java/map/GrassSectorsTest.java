package map;

import org.junit.jupiter.api.Test;
import vector2d.Vector2d;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GrassSectorsTest {

  @Test
  void checkIfGrassIsCorrectlyPlanted()
  {
    WorldMap map = new WorldMap(1, 1, 0.5);
    ArrayList <Vector2d> list = new ArrayList<>();
    list.add(new Vector2d(0,0));
    list.add(new Vector2d(0,0));
    Map<Vector2d, Field> fields = new LinkedHashMap<>();
    GrassSectors sector = new GrassSectors(0, list, map);
    sector.plantGrass(fields);
    assertTrue(fields.containsKey(new Vector2d(0,0)));
  }
}