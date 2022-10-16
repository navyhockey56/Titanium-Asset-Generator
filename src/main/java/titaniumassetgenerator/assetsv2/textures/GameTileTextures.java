package titaniumassetgenerator.assetsv2.textures;

import titaniumassetgenerator.assetsv2.GameTile;
import titaniumassetgenerator.utils.json.JsonObject;

import java.util.function.BiConsumer;

public enum GameTileTextures {
  CHECKERED(new CheckeredTexture()),
  BORDERED(new BorderedTexture());

  public final BiConsumer<GameTile, JsonObject> applicator;

  GameTileTextures(final BiConsumer<GameTile, JsonObject> applicator) {
    this.applicator = applicator;
  }
}
