package titaniumassetgenerator.utils;

import titaniumassetgenerator.utils.json.Json;
import titaniumassetgenerator.utils.json.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

public class ResourceLoader {
  private final JsonParser parser;

  public ResourceLoader() {
    this.parser = new JsonParser();
  }

  public File loadResource(final String name) {
    final ClassLoader classLoader = getClass().getClassLoader();
    final URL resource = classLoader.getResource(name);
    try {
      return new File(resource.toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

//  public List<File> loadResources(final String directory) {
//    final ClassLoader classLoader = getClass().getClassLoader();
//    try {
//      List<File> files = new ArrayList<>();
//      for (Iterator<URL> iterator = classLoader.getResources(directory).asIterator(); iterator.hasNext(); ) {
//        URL resource = iterator.next();
//        files.add(new File(resource.toURI());
//      }
//
//      return files;
//    } catch (IOException | URISyntaxException e) {
//      throw new RuntimeException(e);
//    }
//  }

  public BufferedReader resourceReader(final String name) {
    final File resource = this.loadResource(name);
    try {
      return new BufferedReader(new FileReader(resource));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public String readResource(final String name) {
    return this.resourceReader(name).lines().collect(Collectors.joining(""));
  }

  public <T extends Json> T loadJson(String name) {
    if (!name.endsWith(".json")) name += ".json";

    return this.parser.parse(this.readResource(name));
  }
}
