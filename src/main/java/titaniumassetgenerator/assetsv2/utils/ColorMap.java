package titaniumassetgenerator.assetsv2.utils;

import titaniumassetgenerator.utils.SimpleMap;
import titaniumassetgenerator.utils.Tuple;

import java.awt.Color;

public class ColorMap {
  private SimpleMap map;

  public ColorMap() {
    this.map = new SimpleMap();
  }

  public void put(int x, int y, Color color) {
    this.map.put(new Tuple(x, y), color);
  }

  public Color get(int x, int y) {
    return this.map.get(new Tuple(x, y));
  }

  public String toString() {
    return this.map.toString();
  }
}
