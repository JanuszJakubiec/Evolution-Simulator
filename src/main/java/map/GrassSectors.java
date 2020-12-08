package map;

import vector2d.Vector2d;

import java.util.ArrayList;
import java.util.LinkedList;

public class GrassSectors {
  private GrassSectors sectorInside;
  private final Vector2d leftBottomCorner;
  private final Vector2d rightTopCorner;

  public GrassSectors(int n, ArrayList<Vector2d> array)
  {
    leftBottomCorner = array.get(0);
    rightTopCorner = array.get(1);
  }

}
