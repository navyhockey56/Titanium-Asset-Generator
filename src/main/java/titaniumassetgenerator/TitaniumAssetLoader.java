package titaniumassetgenerator;

import titaniumassetgenerator.assets.map.MapAsset;
import titaniumassetgenerator.assets.map.components.ComponentAsset;
import titaniumassetgenerator.assetsv2.GameMapComponentTypes;
import titaniumassetgenerator.assets.map.components.SingleTileComponentAsset;
import titaniumassetgenerator.assets.map.tiles.TileAsset;
import titaniumassetgenerator.utils.ResourceLoader;
import titaniumassetgenerator.utils.SimpleMap;
import titaniumassetgenerator.utils.json.JsonObject;

public class TitaniumAssetLoader {
  private static TitaniumAssetLoader instance;
  public static TitaniumAssetLoader getInstance() {
    if (instance == null) {
      instance = new TitaniumAssetLoader();
    }

    return instance;
  }

  private final ResourceLoader resourceLoader;
  private final SimpleMap assetMap;

  private TitaniumAssetLoader() {
    this.resourceLoader = new ResourceLoader();
    this.assetMap = new SimpleMap();
  }

  public JsonObject loadAssetData(final String assetName) {
    return this.resourceLoader.loadJson(assetName);
  }

  public MapAsset loadMapAsset(final String assetName) {
    MapAsset asset = this.assetMap.get(assetName);
    if (asset != null) return asset;

    asset = new MapAsset(this.loadAssetData(assetName));
    this.assetMap.put(assetName, asset);
    return asset;
  }

  public ComponentAsset loadComponentAsset(final JsonObject assetDefinition) {
    final String type = assetDefinition.getItem("type");
    if (type != null) {
      switch (GameMapComponentTypes.valueOf(type)) {
        case SINGLE_TILE:
          return new SingleTileComponentAsset(assetDefinition);
        default:
          throw new RuntimeException("Unknown type");
      }
    }

    final String assetName = assetDefinition.getItem("name");
    ComponentAsset asset = this.assetMap.get(assetName);
    if (asset != null) return asset;

    asset = new ComponentAsset(this.loadAssetData(assetName));
    this.assetMap.put(assetName, asset);
    return asset;
  }

  public TileAsset loadTileAsset(final JsonObject assetDefinition) {
    final String type = assetDefinition.getItem("type");
    if (type != null) {
      return new TileAsset(type, assetDefinition.get("parameters"), assetDefinition.get("textures"));
    }

    final String name = assetDefinition.getItem("name");
    TileAsset asset = this.assetMap.get(name);
    if (asset != null) return asset;

    final JsonObject assetData = this.loadAssetData(name);
    asset = new TileAsset(assetData.getItem("type"), assetData.get("parameters"), assetData.get("textures"));
    this.assetMap.put(name, asset);
    return asset;
  }

//  public TileAsset loadTileAsset(final String assetName) {
//    TileAsset asset = this.assetMap.get(assetName);
//    if (asset != null) return asset;
//
//    asset = new TileAsset(this.loadAssetData(assetName));
//    this.assetMap.put(assetName, asset);
//    return asset;
//  }
}
