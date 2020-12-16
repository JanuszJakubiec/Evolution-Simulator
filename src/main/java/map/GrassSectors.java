package map;

import observer.IFieldAvailabilityObserver;
import observer.IFieldAvailabilityPublisher;
import vector2d.Vector2d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class GrassSectors implements IFieldAvailabilityPublisher {
  private GrassSectors sectorInside = null;
  private final Vector2d leftBottomCorner;
  private final Vector2d rightTopCorner;
  private final FreeFields freeFields;
  LinkedList<IFieldAvailabilityObserver> subscribers = new LinkedList<>();

  public GrassSectors(int n, ArrayList<Vector2d> array, WorldMap map)
  {
    leftBottomCorner = array.get(0);
    rightTopCorner = array.get(1);
    array.remove(0);
    array.remove(0);
    if(n!=0)
    {
      sectorInside = new GrassSectors(n-1, array, map);
    }
    freeFields = new FreeFields(map, this);
    markFreeFields();
  }

  public void plantGrass(Map<Vector2d, Field> fields)
  {
    if(sectorInside != null)
      sectorInside.plantGrass(fields);
    if(freeFields.isFreeFieldAvailable())
    {
      Vector2d position = freeFields.placeGrass();
      Field newField = new Field(position);
      newField.plantGrass();
      fields.put(position, newField);
    }
  }

  @Override
  public void addObserver(IFieldAvailabilityObserver observer)
  {
    subscribers.add(observer);
  }

  @Override
  public void removeObserver(IFieldAvailabilityObserver observer)
  {
    subscribers.remove(observer);
  }


  private boolean doesPointBelongTo(Vector2d point)
  {
    if(point.follows(leftBottomCorner) && point.precedes(rightTopCorner))
      return (sectorInside == null || !sectorInside.doesPointBelongTo(point));
    return false;
  }

  private void markFreeFields()
  {
    for(int i= leftBottomCorner.x; i<=rightTopCorner.x;i++)
    {
      for(int j= leftBottomCorner.y; j<=rightTopCorner.y; j++)
      {
        Vector2d point = new Vector2d(i,j);
        if(this.doesPointBelongTo(point))
        {
          newFreePosition(point);
        }
      }
    }
  }

  private void newFreePosition(Vector2d position)
  {
    for(IFieldAvailabilityObserver observer:subscribers)
    {
      observer.setPositionAsAvailable(position);
    }
  }
}
