package titaniumassetgenerator.assets.map;

import titaniumassetgenerator.TitaniumAssetLoader;
import titaniumassetgenerator.assets.Asset;
import titaniumassetgenerator.assets.map.components.ComponentAsset;
import titaniumassetgenerator.assets.map.tiles.TileAsset;
import titaniumassetgenerator.assetsv2.utils.ColorMap;
import titaniumassetgenerator.assetsv2.utils.Colors;
import titaniumassetgenerator.assetsv2.utils.Painter;
import titaniumassetgenerator.utils.json.JsonArray;
import titaniumassetgenerator.utils.json.JsonObject;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

public class MapAsset implements Asset {
  private class MapComponent {
    private final int xPosition;
    private final int yPosition;
    private final ComponentAsset asset;

    MapComponent(final int xPosition, final int yPosition, final ComponentAsset asset) {
      this.xPosition = xPosition;
      this.yPosition = yPosition;
      this.asset = asset;
    }
  }

  private final int height;
  private final int width;

  private final Color backgroundColor;
  private final List<MapComponent> mapComponents;

  private ColorMap colorMap;

  public MapAsset(final JsonObject mapData) {
    this.height = mapData.digItem("height");
    this.width = mapData.digItem("width");

    this.backgroundColor = Colors.loadColor(mapData.dig("backgroundColor"));

    final TitaniumAssetLoader loader = TitaniumAssetLoader.getInstance();
    final JsonArray assetsArray = mapData.get("assets");
    this.mapComponents = assetsArray.stream().map(json -> {
      final JsonObject assetData = (JsonObject) json;
      return new MapComponent(
          assetData.getItem("xPosition"),
          assetData.getItem("yPosition"),
          loader.loadComponentAsset(assetData)
      );
    }).collect(Collectors.toList());
  }

  public int width() {
    return this.width;
  }

  public int height() {
    return this.height;
  }

  public Color valueAt(int x, int y) {
    return colorMap.get(x, y);
  }

  public void load() {
    if (this.colorMap != null) {
      return;
    }

    this.colorMap = new ColorMap();
    this.paintBackground(this.backgroundColor, this.colorMap);

    this.mapComponents.stream().forEach((mapComponent) -> {
      mapComponent.asset.load();

      final int xPosition = mapComponent.xPosition * TileAsset.TILE_WIDTH;
      final int yPosition = mapComponent.yPosition * TileAsset.TILE_HEIGHT;
      Painter.paint(
          xPosition,
        mapComponent.asset.width() * TileAsset.TILE_WIDTH,
          yPosition,
         mapComponent.asset.height() * TileAsset.TILE_HEIGHT,
        (x, y) -> mapComponent.asset.valueAt(x - xPosition, y - yPosition),
        (x, y, color) -> this.colorMap.put(x, y, color)
      );
    });
  }
}
