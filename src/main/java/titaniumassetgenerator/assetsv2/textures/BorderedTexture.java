package titaniumassetgenerator.assetsv2.textures;

import titaniumassetgenerator.assetsv2.GameTile;
import titaniumassetgenerator.assetsv2.TileDimensions;
import titaniumassetgenerator.assetsv2.utils.Colors;
import titaniumassetgenerator.assetsv2.utils.Painter;
import titaniumassetgenerator.utils.json.JsonObject;

import java.awt.Color;
import java.util.function.BiConsumer;

public class BorderedTexture implements BiConsumer<GameTile, JsonObject> {
  @Override
  public void accept(GameTile gameTile, JsonObject parameters) {
    final Color borderColor = Colors.loadColor(parameters.get("color"));
    final int borderWidth = parameters.getItem("width");

    if (borderWidth >= (TileDimensions.WIDTH / 2) || borderWidth >= (TileDimensions.HEIGHT / 2)) {
      Painter.paintColor(
        0, TileDimensions.WIDTH,
        0, TileDimensions.HEIGHT,
        borderColor, gameTile::setColorAt
      );

      return;
    }

    Painter.paintColor(
      0, TileDimensions.WIDTH,
      0, borderWidth,
      borderColor, gameTile::setColorAt
    );

    Painter.paintColor(
      0, TileDimensions.WIDTH,
      TileDimensions.HEIGHT - (borderWidth + 1), TileDimensions.HEIGHT,
      borderColor, gameTile::setColorAt
    );

    Painter.paintColor(
      0, borderWidth,
      0, TileDimensions.HEIGHT,
      borderColor, gameTile::setColorAt
    );

    Painter.paintColor(
      TileDimensions.WIDTH - (borderWidth + 1), TileDimensions.WIDTH,
      0, TileDimensions.HEIGHT,
      borderColor, gameTile::setColorAt
    );
  }
}
