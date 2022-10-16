package titaniumassetgenerator.assets;

import titaniumassetgenerator.assets.map.tiles.TileAsset;
import titaniumassetgenerator.assetsv2.utils.ColorMap;
import titaniumassetgenerator.assetsv2.utils.Painter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface Asset {
  int width();
  int height();

  Color valueAt(int x, int y);

  void load();

  default BufferedImage toImage() {
    this.load();

    final int width = this.width() * TileAsset.TILE_WIDTH;
    final int height = this.height() * TileAsset.TILE_HEIGHT;
    final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Painter.paint(
        0,
        width,
        0,
        height,
      (x, y) -> this.valueAt(x, y),
      (x, y, color) -> image.setRGB(x, y, color.getRGB())
    );

    return image;
  }

  default File toFile(final String filePath) {
    try {
      File outputFile = new File(filePath);
      ImageIO.write(this.toImage(), "png", outputFile);
      return outputFile;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  default void paintBackground(
      final Color color,
      final ColorMap colorMap) {

    Painter.paintColor(
        0,
        this.width() * TileAsset.TILE_WIDTH,
        0,
        this.height() * TileAsset.TILE_HEIGHT,
        null,
        (x, y, _color) -> colorMap.put(x, y, color)
    );
  }
}
