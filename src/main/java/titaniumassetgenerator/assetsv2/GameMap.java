package titaniumassetgenerator.assetsv2;

import titaniumassetgenerator.assets.map.tiles.TileAsset;
import titaniumassetgenerator.assetsv2.utils.GameTiles;
import titaniumassetgenerator.assetsv2.utils.Painter;
import titaniumassetgenerator.utils.Logic;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameMap {
  private final int width;
  private final int height;

  private final GameTiles tiles;

  public GameMap(final int width, final int height) {
    this.width = width;
    this.height = height;

    this.tiles = new GameTiles(width, height);
  }

  public void setTile(final int x, final int y, final GameTile tile) {
    this.tiles.setTile(x, y, tile);
  }

  public GameTile tileAt(int x, int y) {
    return this.tiles.tileAt(x, y);
  }

  public GameTile tileContainingPixel(int x, int y) {
    return this.tileAt(x / TileAsset.TILE_WIDTH, y / TileAsset.TILE_HEIGHT);
  }

  public Color getColorForPixel(int x, int y) {
    final GameTile tile = this.tileContainingPixel(x, y);
    if (tile == null) return null;

    return tile.getColorAt(x % TileAsset.TILE_WIDTH, y % TileAsset.TILE_HEIGHT);
  }

  public BufferedImage toImage() {
    final int width = this.width * TileAsset.TILE_WIDTH;
    final int height = this.height * TileAsset.TILE_HEIGHT;
    final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Painter.paint(
      0, width,0, height,
      (x, y) -> Logic.or(this.getColorForPixel(x, y), Color.BLACK),
      (x, y, color) -> image.setRGB(x, y, color.getRGB())
    );

    return image;
  }

  public File toImageFile(final String filePath) {
    try {
      File outputFile = new File(filePath);
      ImageIO.write(this.toImage(), "png", outputFile);
      return outputFile;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
