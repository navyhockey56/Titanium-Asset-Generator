package titaniumassetgenerator.assetsv2.loaders;

import titaniumassetgenerator.assetsv2.GameMapComponentTypes;
import titaniumassetgenerator.assetsv2.GameMapComponent;
import titaniumassetgenerator.assetsv2.GameTile;
import titaniumassetgenerator.utils.Logic;
import titaniumassetgenerator.utils.Looping;
import titaniumassetgenerator.utils.ResourceLoader;
import titaniumassetgenerator.utils.json.JsonArray;
import titaniumassetgenerator.utils.json.JsonObject;

import java.util.function.Consumer;

public class GameMapComponentLoader {
  private final GameTileLoader gameTileLoader;
  private final ResourceLoader resourceLoader;

  public GameMapComponentLoader() {
    this.resourceLoader = new ResourceLoader();
    this.gameTileLoader = new GameTileLoader(this.resourceLoader);
  }

  public GameMapComponentLoader(final GameTileLoader gameTileLoader, final ResourceLoader resourceLoader) {
    this.gameTileLoader = gameTileLoader;
    this.resourceLoader = resourceLoader;
  }

  public GameMapComponent loadMapComponent(final String resourceName) {
    return this.loadMapComponent(this.resourceLoader.loadJson(resourceName));
  }

  public GameMapComponent loadMapComponent(final JsonObject resourceDefinition) {
    switch (GameMapComponentTypes.valueOf(resourceDefinition.getItem("type"))) {
      case SINGLE_TILE:
        return loadType_SingleTile(resourceDefinition);
      case EXPLICIT:
        return loadType_Basic(resourceDefinition);
      default:
        throw new RuntimeException("Unimplemented type");
    }
  }

  private GameMapComponent loadType_SingleTile(final JsonObject resourceDefinition) {
    final JsonObject tileResource = ((JsonArray) resourceDefinition.get("tiles")).first();
    final GameTile tile = this.loadTile(tileResource);

    final int width = resourceDefinition.getItem("width");
    final int height = resourceDefinition.getItem("height");
    final GameMapComponent mapComponent = new GameMapComponent(width, height);
    Looping.forEach(width, height, (x, y) -> {
      mapComponent.setTile(x, y, tile);
    });

    return mapComponent;
  }

  private GameMapComponent loadType_Basic(final JsonObject resourceDefinition) {
    final int width = resourceDefinition.getItem("width");
    final int height = resourceDefinition.getItem("height");
    final GameMapComponent mapComponent = new GameMapComponent(width, height);

    final JsonArray tileResources = resourceDefinition.getItem("tiles");
    tileResources.forEach((Consumer<JsonObject>) tileResource -> {
      final GameTile tile = this.loadTile(tileResource);
      final int x = tileResource.digItem("position", "x");
      final int y = tileResource.digItem("position", "y");
      mapComponent.setTile(x, y, tile);
    });

    return mapComponent;
  }

  private GameTile loadTile(final JsonObject tileResource) {
    final String resource = tileResource.getItem("resource");
    if (Logic.isTruthy(resource)) return this.gameTileLoader.loadTile(resource);

    return this.gameTileLoader.loadTile(tileResource.get("definition"));
  }
}
