package titaniumassetgenerator.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Tuple {
  private final Object[] values;

  public Tuple(final Object... values) {
    this.values = values;
  }

  public <T> T get(int index) {
    if (index >= this.values.length) {
      return null;
    }

    return (T) this.values[index];
  }

  public int length() {
    return this.values.length;
  }

  public boolean equals(Object other) {
    if (!(other instanceof Tuple)) return false;

    final Tuple tuple = (Tuple) other;
    if (tuple.length() != this.length()) return false;

    for (int i = 0; i < this.length(); i++) {
      if (!this.get(i).equals(tuple.get(i))) return false;
    }

    return true;
  }

  public int hashCode() {
    return Arrays.stream(this.values)
        .map(item -> String.valueOf(item.hashCode()))
        .collect(Collectors.joining(":"))
        .hashCode();
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("Tuple(");
    builder.append(Arrays.stream(this.values).map(Object::toString).collect(Collectors.joining(", ")));
    builder.append(")");
    return builder.toString();
  }
}
