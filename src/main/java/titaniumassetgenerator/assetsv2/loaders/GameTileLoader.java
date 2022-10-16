package titaniumassetgenerator.assetsv2.loaders;

import titaniumassetgenerator.assetsv2.GameTile;
import titaniumassetgenerator.assetsv2.GameTileTypes;
import titaniumassetgenerator.assetsv2.TileDimensions;
import titaniumassetgenerator.assetsv2.textures.GameTileTextures;
import titaniumassetgenerator.assetsv2.utils.Colors;
import titaniumassetgenerator.utils.Looping;
import titaniumassetgenerator.utils.ResourceLoader;
import titaniumassetgenerator.utils.json.JsonArray;
import titaniumassetgenerator.utils.json.JsonObject;

import java.awt.Color;
import java.util.function.Consumer;

public class GameTileLoader {
  private final ResourceLoader resourceLoader;

  public GameTileLoader() {
    this(new ResourceLoader());
  }

  public GameTileLoader(final ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public GameTile loadTile(final String resourceName) {
    return this.loadTile(this.resourceLoader.loadJson(resourceName));
  }

  public GameTile loadTile(final JsonObject resourceDefinition) {
    switch (GameTileTypes.valueOf(resourceDefinition.getItem("type"))) {
      case SINGLE_COLOR:
        return this.loadType_SingleColor(resourceDefinition);
      case EXPLICIT:
        return this.loadType_Explicit(resourceDefinition);
      default:
        throw new RuntimeException("Unimplemented type");
    }
  }

  private GameTile loadType_SingleColor(final JsonObject resourceDefinition) {
    final GameTile gameTile = new GameTile();
    final Color color = Colors.loadColor(resourceDefinition.get("color"));

    Looping.forEach(TileDimensions.WIDTH, TileDimensions.HEIGHT, (x, y) -> gameTile.setColorAt(x, y, color));

    this.applyTextures(gameTile, resourceDefinition);

    return gameTile;
  }

  private GameTile loadType_Explicit(final JsonObject resourceDefinition) {
    final GameTile gameTile = new GameTile();

    final JsonArray pixels = resourceDefinition.get("pixels");
    pixels.forEach((Consumer<JsonObject>) json -> {
      gameTile.setColorAt(json.getItem("x"), json.getItem("y"), Colors.loadColor(json.get("color")));
    });

    this.applyTextures(gameTile, resourceDefinition);

    return gameTile;
  }

  private void applyTextures(final GameTile tile, final JsonObject resourceDefinition) {
    final JsonArray textures = resourceDefinition.get("textures");
    if (textures == null) return;

    textures.forEach((Consumer<JsonObject>) textureDefinition -> {
      final GameTileTextures texture = GameTileTextures.valueOf(textureDefinition.getItem("type"));
      texture.applicator.accept(tile, textureDefinition);
    });
  }
}
