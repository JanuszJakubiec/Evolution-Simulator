package animal;
import vector2d.*;

import java.util.Objects;

public class AnimalOrientation {
  private MapDirection orientation = MapDirection.NORTH;

  public AnimalOrientation()
  {
    rotate((int)(Math.random() * 8));
  }

  @Override
  public String toString() {
    return switch (orientation) {
      case NORTH -> "0";
      case SOUTH -> "4";
      case EAST -> "2";
      case WEST -> "6";
      case NORTHEAST -> "1";
      case NORTHWEST -> "7";
      case SOUTHEAST -> "3";
      case SOUTHWEST -> "5";
    };
  }

  public MapDirection getOrientation() {
    return orientation;
  }

  public Vector2d step()
  {
    return orientation.toUnitVector();
  }

  public void rotate(int n)
  {
    MapDirection tmp = orientation;
    for(int i =0; i<(n%8);i++)
    {
      tmp = tmp.next();
    }
    orientation = tmp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AnimalOrientation that = (AnimalOrientation) o;
    return orientation == that.orientation;
  }

  @Override
  public int hashCode() {
    return Objects.hash(orientation);
  }
}
