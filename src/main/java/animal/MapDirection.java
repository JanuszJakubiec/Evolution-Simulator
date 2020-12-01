package animal;
import vector2d.*;

enum MapDirection
{
  NORTH,
  SOUTH,
  WEST,
  EAST,
  NORTHEAST,
  NORTHWEST,
  SOUTHEAST,
  SOUTHWEST;

  public String toString()
  {
    switch(this)
    {
      case NORTH: return "Północ";
      case SOUTH: return "Południe";
      case WEST: return "Zachód";
      case EAST: return "Wschód";
      case NORTHEAST: return "Północny Wschód";
      case NORTHWEST: return "Północny Zachód";
      case SOUTHEAST: return "Południowy Wschód";
      case SOUTHWEST: return "Północny Zachód";
    }
    throw new IllegalArgumentException("Error, invalid data");
  }

  public MapDirection next()
  {
    switch(this)
    {
      case NORTH: return NORTHEAST;
      case NORTHEAST: return EAST;
      case EAST: return SOUTHEAST;
      case SOUTHEAST: return SOUTH;
      case SOUTH: return SOUTHWEST;
      case SOUTHWEST: return WEST;
      case WEST: return NORTHWEST;
      case NORTHWEST: return NORTH;
    }
    throw new IllegalArgumentException("Error, invalid data");
  }

  public Vector2d toUnitVector()
  {
    switch(this)
    {
      case EAST: return new Vector2d(1,0);
      case SOUTH: return new Vector2d(0,-1);
      case WEST: return new Vector2d(-1,0);
      case NORTH: return new Vector2d(0,1);
      case NORTHWEST: return new Vector2d(-1,1);
      case NORTHEAST: return new Vector2d(1,1);
      case SOUTHWEST: return new Vector2d(-1,-1);
      case SOUTHEAST: return new Vector2d(1,-1);
    }
    throw new IllegalArgumentException("Error, invalid data");
  }
}