package titaniumassetgenerator.assetsv2;

import titaniumassetgenerator.assetsv2.utils.GameTiles;

public class GameMapComponent {
  private final int width;
  private final int height;

  private final GameTiles tiles;

  public GameMapComponent(final int width, final int height) {
    this.width = width;
    this.height = height;

    this.tiles = new GameTiles(width, height);
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public void setTile(final int x, final int y, final GameTile tile) {
    this.tiles.setTile(x, y, tile);
  }

  public GameTile tileAt(int x, int y) {
    return this.tiles.tileAt(x, y);
  }
}
