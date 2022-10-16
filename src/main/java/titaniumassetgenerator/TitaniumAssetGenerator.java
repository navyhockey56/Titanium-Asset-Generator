package titaniumassetgenerator;

import titaniumassetgenerator.assetsv2.GameMap;
import titaniumassetgenerator.assetsv2.loaders.GameMapLoader;
import titaniumassetgenerator.utils.BasicLogger;

class TitaniumAssetGenerator {
//  private static class TitaniumAssetGeneratorArgs {
//    private final String assetName;
//
//    TitaniumAssetGeneratorArgs(String[] args) {
//      if (args.length < 1) {
//        throw new RuntimeException("You must provide arguments");
//      }
//
//      this.assetName = args[0];
//    }
//
//    public String getAssetName() {
//      return this.assetName;
//    }
//  }

  public static void main(String[] args) {
    final BasicLogger logger = BasicLogger.logger();
    // final TitaniumAssetGeneratorArgs userArgs = new TitaniumAssetGeneratorArgs(args);

//    MapAsset map = TitaniumAssetLoader.getInstance().loadMapAsset("maps/simple-town");
//    map.toFile("/home/will/Pokemon-Titanium/Titanium-Asset-Generator/output/simple-town.png");

    final GameMapLoader mapLoader = new GameMapLoader();
    final GameMap gameMap = mapLoader.loadMap("maps/game-map");
    gameMap.toImageFile("/home/will/Pokemon-Titanium/Titanium-Asset-Generator/output/game-map.png");
  }
}
