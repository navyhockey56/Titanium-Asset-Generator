package titaniumassetgenerator.assetsv2.utils;

import titaniumassetgenerator.assetsv2.GameTile;

public class GameTiles {
  private final GameTile[][] tiles;

  public GameTiles(final int width, final int height) {
    this.tiles = new GameTile[width][height];
    // for (int i = 0; i < width; i++) this.tiles[i] = new GameTile[height];
  }

  public void setTile(final int x, final int y, final GameTile tile) {
    if (x < 0 || x >= this.tiles.length) return;

    final GameTile[] row = this.tiles[x];
    if (y < 0 || y >= row.length) return;

    row[y] = tile;
  }

  public GameTile tileAt(int x, int y) {
    if (x < 0 || x >= this.tiles.length) return null;

    final GameTile[] row = this.tiles[x];
    if (y < 0 || y >= row.length) return null;

    return row[y];
  }
}
