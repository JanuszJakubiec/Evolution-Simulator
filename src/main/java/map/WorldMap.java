package map;
import vector2d.*;
import animal.*;

import java.util.*;

public class WorldMap {
  private Map<Vector2d, Field> animals = new LinkedHashMap<>();
  private Vector2d leftBottomCorner = new Vector2d(0,0);
  private Vector2d rightTopCorner;

  public WorldMap(int width, int height)
  {

  }
}
