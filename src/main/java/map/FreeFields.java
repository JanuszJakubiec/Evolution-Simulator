package map;

import vector2d.Vector2d;

import java.util.ArrayList;

public class FreeFields {
  private final ArrayList<Vector2d> free = new ArrayList<>();

  public FreeFields()
  {
  }

  public void add(Vector2d position)
  {
    free.add(position);
  }

  public void remove(Vector2d position)
  {
    free.remove(position);
  }

  public boolean isFreeFieldAvailable()
  {
    return free.size() > 0;
  }

  public Vector2d placeGrass()
  {
    int number = (int) (Math.random() * free.size());
    Vector2d place = free.get(number);
    free.remove(number);
    return place;
  }
}
