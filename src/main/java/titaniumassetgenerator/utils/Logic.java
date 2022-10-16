package titaniumassetgenerator.utils;

public class Logic {
  public static <T> T or(final Object left, final Object right) {
    if (isTruthy(left)) return (T) left;

    return (T) right;
  }

  public static <T> T and(final Object left, final Object right) {
    if (isTruthy(left) && isTruthy(right)) return (T) right;

    return null;
  }

  public static boolean isTruthy(final Object object) {
    return object != null && !object.equals(false);
  }

  public static boolean isFalsey(final Object object) {
    return !isTruthy(object);
  }
}
