package titaniumassetgenerator.assetsv2.loaders;

import titaniumassetgenerator.assetsv2.GameMap;
import titaniumassetgenerator.assetsv2.GameMapComponent;
import titaniumassetgenerator.assetsv2.GameTile;
import titaniumassetgenerator.utils.Logic;
import titaniumassetgenerator.utils.Looping;
import titaniumassetgenerator.utils.ResourceLoader;
import titaniumassetgenerator.utils.json.JsonArray;
import titaniumassetgenerator.utils.json.JsonObject;

import java.util.function.Consumer;

public class GameMapLoader {
  private final GameMapComponentLoader gameMapComponentLoader;
  private final GameTileLoader gameTileLoader;

  private final ResourceLoader resourceLoader;

  public GameMapLoader() {
    this.resourceLoader = new ResourceLoader();
    this.gameTileLoader = new GameTileLoader(this.resourceLoader);
    this.gameMapComponentLoader = new GameMapComponentLoader(this.gameTileLoader, this.resourceLoader);
  }

  public GameMapLoader(
      final GameTileLoader gameTileLoader,
      final GameMapComponentLoader gameMapComponentLoader,
      final ResourceLoader resourceLoader
  ) {
    this.gameMapComponentLoader = gameMapComponentLoader;
    this.gameTileLoader = gameTileLoader;
    this.resourceLoader = resourceLoader;
  }

  public GameMap loadMap(final String resourcePath) {
    final JsonObject mapResource = this.resourceLoader.loadJson(resourcePath);
    return this.loadMap(mapResource);
  }

  /**
   * {
   *   "width": Integer,
   *   "height": Integer,
   *   "tiles": Array(JsonObject : Tile),
   *   "components": Array(JsonObject: MapComponent)
   * }
   *
   * JsonObject Tile:
   * {
   *   "resource": Optional(String) - The name of the resource to load,
   *   "definition": Optional(JsonObject - TileResource) - The inline definition of the tile resource,
   *   "position": {
   *     "x": Integer,
   *     "y": Integer
   *   }
   * }
   *
   * JsonObject MapComponent:
   * {
   *   "resource": Optional(String) - The name of the resource to load,
   *   "definition": Optional(JsonObject - MapComponentResource) - The inline definition of the map component resource,
   *   "position": {
   *     "x": Integer,
   *     "y": Integer
   *   }
   * }
   * @param mapResource
   * @return
   */
  public GameMap loadMap(final JsonObject mapResource) {
    final GameMap gameMap = new GameMap(mapResource.getItem("width"), mapResource.getItem("height"));

    final JsonArray tiles = mapResource.get("tiles");
    tiles.forEach(this.mapTile(gameMap));

    final JsonArray mapComponents = mapResource.get("components");
    mapComponents.forEach(this.mapComponent(gameMap));

    return gameMap;
  }

  private Consumer<JsonObject> mapTile(final GameMap gameMap) {
    return (resourceJson) -> {
      final String resourceName = resourceJson.getItem("resource");
      final GameTile tile;
      if (Logic.isTruthy(resourceName)) {
        tile = this.gameTileLoader.loadTile(resourceName);
      } else {
        tile = this.gameTileLoader.loadTile((JsonObject) resourceJson.get("definition"));
      }

      // Store the tile within the map
      gameMap.setTile(
        resourceJson.digItem("position", "x"),
        resourceJson.digItem("position", "y"),
        tile
      );
    };
  }

  private Consumer<JsonObject> mapComponent(final GameMap gameMap) {
    return (resourceJson) -> {
      final String resourceName = resourceJson.getItem("resource");
      final GameMapComponent mapComponent;
      if (Logic.isTruthy(resourceName)) {
        mapComponent = this.gameMapComponentLoader.loadMapComponent(resourceName);
      } else {
        mapComponent = this.gameMapComponentLoader.loadMapComponent((JsonObject) resourceJson.get("definition"));
      }

      // Copy the tiles into the map
      Looping.forEach(mapComponent.getWidth(), mapComponent.getHeight(), (x, y) -> {
        gameMap.setTile(
          x + (Integer) resourceJson.digItem("position", "x"),
          y + (Integer) resourceJson.digItem("position", "y"),
          mapComponent.tileAt(x, y)
        );
      });
    };
  }
}
