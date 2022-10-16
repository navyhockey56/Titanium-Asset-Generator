package titaniumassetgenerator.assets.map.tiles;

import titaniumassetgenerator.assetsv2.utils.Colors;
import titaniumassetgenerator.assetsv2.utils.Painter;

import java.awt.Color;

public enum TileTextures {
  CHECKERED((parameters, colorMap) -> {
    final Color checkerColor = Colors.loadColor(parameters.get("color"));
    final Painter.ColorSetter setter = (x, y, color) -> colorMap.put(x, y, color);
    Painter.paintColor(0, 4, 0, 4, checkerColor, setter);
    Painter.paintColor(8, 4, 0, 4, checkerColor, setter);
    Painter.paintColor(16, 4, 0, 4, checkerColor, setter);
    Painter.paintColor(4, 4, 4, 4, checkerColor, setter);
    Painter.paintColor(12, 4, 4, 4, checkerColor, setter);
    Painter.paintColor(0, 4, 8, 4, checkerColor, setter);
    Painter.paintColor(8, 4, 8, 4, checkerColor, setter);
    Painter.paintColor(16, 4, 8, 4, checkerColor, setter);
    Painter.paintColor(4, 4, 12, 4, checkerColor, setter);
    Painter.paintColor(12, 4, 12, 4, checkerColor, setter);
    Painter.paintColor(0, 4, 16, 4, checkerColor, setter);
    Painter.paintColor(8, 4, 16, 4, checkerColor, setter);
    Painter.paintColor(16, 4, 16, 4, checkerColor, setter);
  });

  public final TileTexture texture;

  TileTextures(final TileTexture texture) {
    //this.parameters = parameters;
    this.texture = texture;
  }
}
