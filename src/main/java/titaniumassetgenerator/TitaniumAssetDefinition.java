package titaniumassetgenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TitaniumAssetDefinition {
  TitaniumAssetDefinition(String fileName) throws FileNotFoundException {
    new BufferedReader(new FileReader(fileName)).lines();
  }
}
