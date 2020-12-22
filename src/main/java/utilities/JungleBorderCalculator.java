package utilities;

import vector2d.Vector2d;

import java.util.ArrayList;

public class JungleBorderCalculator {
  static public ArrayList<Vector2d> calculate(int width, int height, double ratio)
  {
    ArrayList<Vector2d> list = new ArrayList<>();
    list.add(new Vector2d(0,0));
    list.add(new Vector2d(width-1, height-1));
    int x = (int) ((int) width*ratio);
    int y = (int) ((int) height*ratio);
    int dx = (width - x)/2;
    int dy = (height - y)/2;
    list.add(new Vector2d(dx,dy));
    list.add(new Vector2d(dx+x-1, dy+y-1));
    return list;
  }
}
