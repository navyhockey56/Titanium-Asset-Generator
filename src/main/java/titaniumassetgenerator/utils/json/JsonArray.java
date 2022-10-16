package titaniumassetgenerator.utils.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonArray extends Json<List<Json>> {
  public JsonArray() {
    super(new ArrayList<>());
  }

  public JsonArray(List<Json> value) {
    super(value);
  }

  public <I extends Json> I get(int index) {
    if (index >= this.value.size()) {
      return null;
    }

    return (I) this.value.get(index);
  }

  public <I> I getItem(int index) {
    if (index >= this.value.size()) {
      return null;
    }

    return ((Json<I>) this.value.get(index)).value();
  }

  public <I> List<I> items() {
    return this.value.stream().map(element -> (I) element.value()).collect(Collectors.toList());
  }

  public JsonArray add(Object item) {
    return this.add(new Json(item));
  }

  public JsonArray add(Json item) {
    this.value.add(item);
    return this;
  }

  public int size() {
    return this.value.size();
  }

  public boolean isEmpty() {
    return this.value.isEmpty();
  }

  public <I extends Json> I first() {
    return this.get(0);
  }

  public <I> I firstItem() {
    return this.getItem(0);
  }

  public <I extends Json> I last() {
    return this.get(this.size() - 1);
  }

  public <I> I lastItem() {
    return this.getItem(this.size() - 1);
  }

  public <T extends Json> JsonArray forEach(final Consumer<T> consumer) {
    this.stream().forEach(json -> consumer.accept((T) json));

    return this;
  }

  public <I extends Json, T> List<T> map(final Function<I, T> mapFunction) {
    final List<T> items = new ArrayList<>(this.size());
    this.forEach((Consumer<I>) entry -> mapFunction.apply(entry));
    return items;
  }

  public <T extends Json> Stream<T> stream() {
    return (Stream<T>) this.value.stream();
  }
}
