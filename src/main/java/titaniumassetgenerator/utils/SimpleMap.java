package titaniumassetgenerator.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleMap {
  private final Map<Object, Object> map;

  public SimpleMap() {
    this(new HashMap<>());
  }

  public SimpleMap(Map<Object, Object> map) {
    this.map = map;
  }

  public <T> T get(final Object key) {
    return this.getOrDefaultTo(key, null);
  }

  public <T> T getOrDefaultTo(final Object key, T defaultValue) {
    if (this.contains(key)) return (T) this.map.get(key);

    return defaultValue;
  }

  public SimpleMap put(final Object key, Object item) {
    this.map.put(key, item);
    return this;
  }

  public <T> T putAndReturnItem(final Object key, T item) {
    this.map.put(key, item);
    return item;
  }

  public boolean contains(final Object key) {
    return this.map.containsKey(key);
  }

  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  public <T> Set<T> keys() {
    return (Set<T>) this.map.keySet();
  }

  public <T> Set<T> values() {
    return new HashSet<T>((Collection<T>) this.map.values());
  }

  public <K, V> SimpleMap forEach(final BiConsumer<K, V> consumer) {
    this.map.entrySet().stream().forEach(entry -> consumer.accept((K) entry.getKey(), (V) entry.getValue()));

    return this;
  }

  public <V, T> SimpleMap map(final Function<V, T> mapper) {
    return this.map((BiFunction<Object, V, T>) (_key, value) -> mapper.apply(value));
  }

  public <K, V, T> SimpleMap map(final BiFunction<K, V, T> mapper) {
    final SimpleMap newMap = new SimpleMap();
    this.forEach((BiConsumer<K, V>) (key, value) -> newMap.put(key, mapper.apply(key, value)));
    return newMap;
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();

    builder.append("SimpleMap{\n\t");

    builder.append(this.map.entrySet().stream()
        .map(entry -> String.format("\"%s\": %s", entry.getKey(), entry.getValue()))
        .collect(Collectors.joining("\n\t")));

    builder.append("}");

    return builder.toString();
  }

  public int hashCode() {
    return this.toString().hashCode();
  }

  public boolean equals(Object other) {
    if (!(other instanceof SimpleMap)) return false;

    final SimpleMap otherMap = (SimpleMap) other;
    if (!otherMap.keys().equals(this.keys())) return false;

    return this.map.entrySet().stream()
      .allMatch(entry -> entry.getValue().equals(otherMap.get(entry.getKey())));
  }
}
