package map;

import vector2d.Vector2d;

import java.util.ArrayList;
import java.util.Map;

public class GrassSectors {
  private GrassSectors sectorInside = null;
  private final Vector2d leftBottomCorner;
  private final Vector2d rightTopCorner;
  private final FreeFields freeFields;

  public GrassSectors(int n, ArrayList<Vector2d> array)
  {
    leftBottomCorner = array.get(0);
    rightTopCorner = array.get(1);
    array.remove(0);
    array.remove(0);
    if(n!=0)
    {
      sectorInside = new GrassSectors(n-1, array);
    }
    freeFields = new FreeFields();
    markFreeFields();
  }

  public void plantGrass(Map<Vector2d, Field> fields)
  {
    if(sectorInside != null)
      sectorInside.plantGrass(fields);
    if(freeFields.isFreeFieldAvailable())
    {
      fields.get(freeFields.placeGrass()).plantGrass();
    }
  }

  private boolean doesPointBelongTo(Vector2d point)
  {
    if(point.follows(leftBottomCorner) && point.precedes(rightTopCorner))
      return (sectorInside == null || !sectorInside.doesPointBelongTo(point));
    return false;
  }

  private void markFreeFields()
  {
    for(int i= leftBottomCorner.x; i<rightTopCorner.x;i++)
    {
      for(int j= leftBottomCorner.y; j< rightTopCorner.y; j++)
      {
        Vector2d point = new Vector2d(i,j);
        if(this.doesPointBelongTo(point))
        {
          freeFields.add(point);
        }
      }
    }
  }
}
