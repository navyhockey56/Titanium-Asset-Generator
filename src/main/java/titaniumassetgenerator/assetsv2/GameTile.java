package titaniumassetgenerator.assetsv2;

import titaniumassetgenerator.assetsv2.utils.ColorMap;
import titaniumassetgenerator.utils.json.JsonArray;
import titaniumassetgenerator.utils.json.JsonObject;

import java.awt.Color;

public class GameTile {
  private final ColorMap colorMap;

  private JsonArray events;
  private boolean isWalkable;
  private boolean isSurfable;
  private boolean isFlyable;

  public GameTile() {
    this(new ColorMap(), new JsonArray(), true, false, false);
  }

  public GameTile(final ColorMap colorMap, final JsonArray events,
                  final boolean isWalkable, final boolean isSurfable, final boolean isFlyable) {
    this.colorMap = colorMap;
    this.events = events;
    this.isWalkable = isWalkable;
    this.isSurfable = isSurfable;
    this.isFlyable = isFlyable;
  }

  public GameTile setColorAt(final int x, final int y, final Color color) {
    this.colorMap.put(x, y, color);
    return this;
  }

  public Color getColorAt(final int x, final int y) {
    return this.colorMap.get(x, y);
  }

  public GameTile appendEvent(final JsonObject event) {
    this.events.add(event);
    return this;
  }

  public GameTile setEvents(final JsonArray events) {
    this.events = events;
    return this;
  }

  public JsonArray getEvents() {
    return this.events;
  }

  public GameTile isWalkable(final boolean isWalkable) {
    this.isWalkable = isWalkable;
    return this;
  }

  public GameTile isSurfable(final boolean isSurfable) {
    this.isSurfable = isSurfable;
    return this;
  }

  public GameTile isFlyable(final boolean isFlyable) {
    this.isFlyable = isFlyable;
    return this;
  }

  public boolean isWalkable() {
    return this.isWalkable;
  }

  public boolean isSurfable() {
    return this.isSurfable;
  }

  public boolean isFlyable() {
    return this.isFlyable;
  }
}
