package titaniumassetgenerator.utils.json;

import titaniumassetgenerator.utils.SimpleMap;

import java.util.function.BiFunction;

public class JsonObject extends Json<SimpleMap> {
  public JsonObject() {
    this(new SimpleMap());
  }

  protected JsonObject(SimpleMap value) {
    super(value);
  }

  public <T> JsonObject put(final String key, final T item) {
    if (item instanceof Json) {
      this.value.put(key, item);
    } else {
      this.put(key, new Json(item));
    }

    return this;
  }

  public <T extends Json> T get(final String key) {
    return this.value.get(key);
  }

  public <T> T getItem(final String key) {
    final Json item = this.get(key);
    if (item == null) return null;

    return (T) item.value();
  }

  public <T> Json<T> dig(String... path) {
    if (path.length == 0) {
      return (Json<T>) this;
    }

    JsonObject current = this;
    for (int i = 0; i < path.length - 1; i++) {
      Json next = current.get(path[i]);
      if (next == null) {
        return null;
      }

      if (!(next instanceof JsonObject)) {
        throw new RuntimeException("Invalid path supplied");
      }

      current = (JsonObject) next;
    }

    return current.get(path[path.length - 1]);
  }

  public <T> T digItem(String... path) {
    Json json = this.dig(path);
    if (json == null) return null;

    return (T) json.value();
  }

  public SimpleMap itemsMap() {
    return this.value.map((BiFunction<String, Json, Object>) (_key, value) -> value.value());
  }

  public String toString() {
    return this.itemsMap().map(Object::toString).toString();
  }
}

