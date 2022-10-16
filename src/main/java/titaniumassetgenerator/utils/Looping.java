package titaniumassetgenerator.utils;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Looping {
  public static void forEach(
      final int outerLoopLength,
      final int innerLoopLength,
      final BiConsumer<Integer, Integer> innerLoopConsumer) {

    forEach(0, outerLoopLength, 0, innerLoopLength, (_i) -> {}, innerLoopConsumer);
  }

  public static void forEach(
    final int outerLoopLength,
    final int innerLoopLength,
    final Consumer<Integer> outerLoopConsumer,
    final BiConsumer<Integer, Integer> innerLoopConsumer) {

    forEach(0, outerLoopLength, 0, innerLoopLength, outerLoopConsumer, innerLoopConsumer);
  }

  public static void forEach(
      final int outerLoopOffset,
      final int outerLoopLength,
      final int innerLoopOffset,
      final int innerLoopLength,
      final BiConsumer<Integer, Integer> innerLoopConsumer) {

    forEach(outerLoopOffset, outerLoopLength, innerLoopOffset, innerLoopLength, (_i) -> {}, innerLoopConsumer);
  }

  public static void forEach(
    final int outerLoopOffset,
    final int outerLoopLength,
    final int innerLoopOffset,
    final int innerLoopLength,
    final Consumer<Integer> outerLoopConsumer,
    final BiConsumer<Integer, Integer> innerLoopConsumer) {

    IntStream.rangeClosed(outerLoopOffset, outerLoopOffset + outerLoopLength).forEach(outerIndex -> {
      outerLoopConsumer.accept(outerIndex);
      IntStream.rangeClosed(innerLoopOffset, innerLoopOffset + innerLoopLength).forEach(innerIndex -> {
        innerLoopConsumer.accept(outerIndex, innerIndex);
      });
    });
  }
}
