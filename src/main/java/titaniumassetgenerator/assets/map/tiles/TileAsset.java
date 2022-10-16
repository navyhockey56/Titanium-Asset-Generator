package titaniumassetgenerator.assets.map.tiles;

import titaniumassetgenerator.assets.Asset;
import titaniumassetgenerator.assetsv2.utils.ColorMap;
import titaniumassetgenerator.assetsv2.utils.Colors;
import titaniumassetgenerator.assetsv2.utils.Painter;
import titaniumassetgenerator.utils.json.JsonArray;
import titaniumassetgenerator.utils.json.JsonObject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TileAsset implements Asset {
  public static final String TILE_TYPE_SOLID_COLOR = "solid-color";

  public static final int TILE_WIDTH = 20;
  public static final int TILE_HEIGHT = 20;

  private final Color backgroundColor;
  private final List<TextureApplicator> textures;
  private ColorMap colorMap;

  private class TextureApplicator {
    private final JsonObject parameters;
    private final TileTexture texture;

    TextureApplicator(final JsonObject parameters, final TileTexture texture) {
      this.parameters = parameters;
      this.texture = texture;
    }

    void apply(final ColorMap colorMap) {
      texture.apply(parameters, colorMap);
    }
  }

  public TileAsset(final String type, final JsonObject parameters, final JsonArray textureDefinitions) {
    switch(type) {
      case TILE_TYPE_SOLID_COLOR:
        this.backgroundColor = Colors.loadColor(parameters.get("color"));
        break;
      default:
        throw new RuntimeException("Unexpected type");
    }

    if (textureDefinitions == null) {
      this.textures = new ArrayList<>();
      return;
    };

    this.textures = textureDefinitions.stream().map(json -> {
      final JsonObject textureDefinition = (JsonObject) json;
      return new TextureApplicator(
        textureDefinition.get("parameters"),
        TileTextures.valueOf(textureDefinition.getItem("name")).texture
      );
    }).collect(Collectors.toList());
  }

  @Override
  public int width() {
    return 1;
  }

  @Override
  public int height() {
    return 1;
  }

  @Override
  public Color valueAt(int x, int y) {
    return this.colorMap.get(x, y);
  }

  @Override
  public void load() {
    if (this.colorMap != null) return;

    this.colorMap = new ColorMap();
    Painter.paint(0, TILE_WIDTH, 0, TILE_HEIGHT,
      (x, y) -> null,
      (x, y, _color) -> this.colorMap.put(x, y, this.backgroundColor)
     );

    this.textures.forEach(applicator -> applicator.apply(this.colorMap));
  }
}
