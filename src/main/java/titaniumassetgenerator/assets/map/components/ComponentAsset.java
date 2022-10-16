package titaniumassetgenerator.assets.map.components;

import titaniumassetgenerator.TitaniumAssetLoader;
import titaniumassetgenerator.assets.Asset;
import titaniumassetgenerator.assets.map.tiles.TileAsset;
import titaniumassetgenerator.assetsv2.utils.ColorMap;
import titaniumassetgenerator.assetsv2.utils.Colors;
import titaniumassetgenerator.assetsv2.utils.Painter;
import titaniumassetgenerator.utils.json.JsonArray;
import titaniumassetgenerator.utils.json.JsonObject;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComponentAsset implements Asset {
  protected static class TileDefinition {
    private final int xPosition;
    private final int yPosition;
    private final int width;
    private final int height;
    private final TileAsset asset;

    TileDefinition(final int xPosition, final int yPosition, final int width, final int height, final TileAsset asset) {
      this.xPosition = xPosition;
      this.yPosition = yPosition;
      this.width = width;
      this.height = height;
      this.asset = asset;
    }

    void paint(final ColorMap colorMap) {
      final int initialXPosition = this.xPosition * TileAsset.TILE_WIDTH;
      final int initialYPosition = this.yPosition * TileAsset.TILE_HEIGHT;

      asset.load();

      IntStream.rangeClosed(0, this.width).forEach(xTile -> {
        IntStream.rangeClosed(0, this.height).forEach(yTile -> {
          final int xPosition = (TileAsset.TILE_WIDTH * xTile) + initialXPosition;
          final int yPosition = (TileAsset.TILE_HEIGHT * yTile) + initialYPosition;

          Painter.paint(
              xPosition,
              TileAsset.TILE_WIDTH,
              yPosition,
              TileAsset.TILE_HEIGHT,
              (x, y) -> asset.valueAt(x - xPosition, y - yPosition),
              (x, y, color) -> colorMap.put(x, y, color)
          );
        });
      });
    }
  }

  private final int width;
  private final int height;
  private final Color backgroundColor;

  private final List<TileDefinition> tiles;

  private ColorMap colorMap;

  public ComponentAsset(final int width, final int height, final Color backgroundColor, final List<TileDefinition> tiles) {
    this.width = width;
    this.height = height;
    this.backgroundColor = backgroundColor;
    this.tiles = tiles;
  }

  public ComponentAsset(final JsonObject componentData) {
    this.width = componentData.getItem("width");
    this.height = componentData.getItem("height");
    this.backgroundColor = Colors.loadColor(componentData.get("backgroundColor"));

    final TitaniumAssetLoader loader = TitaniumAssetLoader.getInstance();
    this.tiles = ((JsonArray) componentData.get("tiles")).stream().map(json -> {
      final JsonObject tileDefinition = (JsonObject) json;
      return new TileDefinition(
          tileDefinition.getItem("xPosition"),
          tileDefinition.getItem("yPosition"),
          tileDefinition.getItem("width"),
          tileDefinition.getItem("height"),
          loader.loadTileAsset(tileDefinition)
      );
    }).collect(Collectors.toList());
  }

  @Override
  public int width() {
    return this.width;
  }

  @Override
  public int height() {
    return this.height;
  }

  @Override
  public Color valueAt(int x, int y) {
    return this.colorMap.get(x, y);
  }

  @Override
  public void load() {
    if (this.colorMap != null) return;

    this.colorMap = new ColorMap();
    if (this.backgroundColor != null) this.paintBackground(this.backgroundColor, this.colorMap);

    this.tiles.forEach(tile -> tile.paint(this.colorMap));
  }
}
