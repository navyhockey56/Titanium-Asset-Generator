package titaniumassetgenerator.assets.map.tiles;

import titaniumassetgenerator.assetsv2.utils.ColorMap;
import titaniumassetgenerator.utils.json.JsonObject;

@FunctionalInterface
public interface TileTexture {
  void apply(final JsonObject parameters, final ColorMap colorMap);
}
