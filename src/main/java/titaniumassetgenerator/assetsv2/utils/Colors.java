package titaniumassetgenerator.assetsv2.utils;

import titaniumassetgenerator.utils.SimpleMap;
import titaniumassetgenerator.utils.json.Json;
import titaniumassetgenerator.utils.json.JsonArray;

import java.awt.Color;

public class Colors {
  private final static SimpleMap COLOR_MAP;
  static {
    COLOR_MAP = new SimpleMap();
    COLOR_MAP.put("black", Color.BLACK);
    COLOR_MAP.put("white", Color.WHITE);
    COLOR_MAP.put("clear", Color.TRANSLUCENT);
    COLOR_MAP.put("blue", Color.blue);
    COLOR_MAP.put("red", Color.red);
    COLOR_MAP.put("green", Color.green);
    COLOR_MAP.put("grass-green", new Color(15, 200, 20));
    COLOR_MAP.put("yellow", Color.yellow);
    COLOR_MAP.put("pink", Color.pink);
    COLOR_MAP.put("orange", Color.orange);
    COLOR_MAP.put("cyan", Color.cyan);
    COLOR_MAP.put("magenta", Color.magenta);
    COLOR_MAP.put("gray", Color.gray);
    COLOR_MAP.put("dark-grey", Color.darkGray);
    COLOR_MAP.put("light-grey", Color.lightGray);
  }

  public static Color get(final String key) {
    return COLOR_MAP.get(key);
  }

  public static Color loadColor(Json colorInfo) {
    if (colorInfo == null) return null;

    if (colorInfo.isA(String.class)) {
      return Colors.get((String) colorInfo.value());
    }

    final JsonArray colorInfoAsArray = (JsonArray) colorInfo;
    return new Color(
        (Integer) colorInfoAsArray.getItem(0), // RED
        (Integer) colorInfoAsArray.getItem(1), // GREEN
        (Integer) colorInfoAsArray.getItem(2)  // BLUE
    );
  }
}
