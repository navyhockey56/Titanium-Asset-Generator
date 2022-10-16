package titaniumassetgenerator.assetsv2.textures;

import titaniumassetgenerator.assetsv2.GameTile;
import titaniumassetgenerator.assetsv2.utils.Colors;
import titaniumassetgenerator.assetsv2.utils.Painter;
import titaniumassetgenerator.utils.json.JsonObject;

import java.awt.Color;
import java.util.function.BiConsumer;

public class CheckeredTexture implements BiConsumer<GameTile, JsonObject> {
  @Override
  public void accept(final GameTile gameTile, final JsonObject parameters) {
    final Color checkerColor = Colors.loadColor(parameters.get("color"));
    final Painter.ColorSetter setter = gameTile::setColorAt;

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
  }
}
