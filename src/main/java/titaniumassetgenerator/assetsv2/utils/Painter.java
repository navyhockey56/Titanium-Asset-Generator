package titaniumassetgenerator.assetsv2.utils;

import titaniumassetgenerator.utils.Looping;

import java.awt.*;

public class Painter {
  @FunctionalInterface
  public interface ColorSetter {
    void apply(int x, int y, Color color);
  }

  @FunctionalInterface
  public interface ColorSupplier {
    Color apply(int x, int y);
  }

  public static void paintColor(
      final int xPosition,
      final int width,
      final int yPosition,
      final int height,
      final Color color,
      final ColorSetter colorSetter
  ) {
    paint(xPosition, width, yPosition, height, (x, y) -> color, colorSetter);
  }

  public static void paint(
      final int xPosition,
      final int width,
      final int yPosition,
      final int height,
      final ColorSupplier colorSupplier,
      final ColorSetter colorSetter
  ) {
    Looping.forEach(
      xPosition, width - 1,
      yPosition, height - 1,
      (x, y) -> colorSetter.apply(x, y, colorSupplier.apply(x, y))
    );
  }
}
