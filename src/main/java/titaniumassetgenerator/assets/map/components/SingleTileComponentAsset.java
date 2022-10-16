package titaniumassetgenerator.assets.map.components;

import titaniumassetgenerator.TitaniumAssetLoader;
import titaniumassetgenerator.assets.map.tiles.TileAsset;
import titaniumassetgenerator.utils.json.JsonObject;

import java.util.Arrays;
import java.util.List;

public class SingleTileComponentAsset extends ComponentAsset {
  private static List<TileDefinition> definition(final int width, final int height, final TileAsset tile) {
    return Arrays.asList(new TileDefinition(0, 0, width, height, tile));
  }

  public SingleTileComponentAsset(int width, int height, TileAsset tile) {
    super(width, height, null, definition(width, height, tile));
  }

  public SingleTileComponentAsset(final JsonObject assetData) {
    this(
        assetData.getItem("width"),
        assetData.getItem("height"),
        TitaniumAssetLoader.getInstance().loadTileAsset(assetData.get("parameters"))
    );
  }
}
