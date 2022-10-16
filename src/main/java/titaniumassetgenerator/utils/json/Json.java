package titaniumassetgenerator.utils.json;

public class Json<T> {
  protected final T value;

  protected Json(T value) {
    this.value = value;
  }

  public boolean isA(Class clazz) {
    return clazz.isInstance(this.value);
  }

  public T value() {
    return this.value;
  }
}
