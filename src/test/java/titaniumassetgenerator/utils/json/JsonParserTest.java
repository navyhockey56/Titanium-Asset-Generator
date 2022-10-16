package titaniumassetgenerator.utils.json;

import org.junit.Before;
import org.junit.Test;
import titaniumassetgenerator.utils.ResourceLoader;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JsonParserTest {
  private JsonParser jsonParser;

  @Before
  public void setUp() {
    this.jsonParser = new JsonParser();
  }

  @Test
  public void testParseNull() {
    final Json nullJson = this.jsonParser.parse("null");

    assertNull(nullJson.value());
  }

  @Test
  public void testParsePositiveInteger() {
    for (int i = 0; i < 1000; i++) {
      final Json<Integer> json = this.jsonParser.parse(String.valueOf(i));
      assertTrue(json.value() == i);
    }
  }

  @Test
  public void testParseNegativeInteger() {
    for (int i = -1; i > -1000; i--) {
      final Json<Integer> json = this.jsonParser.parse(String.valueOf(i));
      assertTrue(json.value() == i);
    }
  }

  @Test
  public void testParsePositiveDouble() {
    for (int i = 0; i < 1000; i++) {
      final double value = i + Math.random();
      final Json<Double> json = this.jsonParser.parse(String.valueOf(value));
      assertTrue(json.value() == value);
    }
  }

  @Test
  public void testParseNegativeDouble() {
    for (int i = -1; i > -1000; i--) {
      final double value = i + Math.random();
      final Json<Double> json = this.jsonParser.parse(String.valueOf(value));
      assertTrue(json.value() == value);
    }
  }

  @Test
  public void testParseString() {
    final List<String> values = Arrays.asList("hello world", "yooo", "5234534", "{}", "[]", "null");
    for (final String value : values) {
      final Json<String> json = this.jsonParser.parse("\"" + value + "\"");
      assertEquals(json.value(), value);
    }
  }

  @Test
  public void testParseEmptyObject() {
    final JsonObject json = this.jsonParser.parse("{}");
    assertTrue(json.value().isEmpty());
  }

  @Test
  public void testParseObjectWithPositiveIntegerValue() {
    final JsonObject json = this.jsonParser.parse("{ \"key\": 123 }");
    final Integer value = json.digItem("key");
    assertTrue(value == 123);
  }

  @Test
  public void testParseObjectWithNegativeIntegerValue() {
    final JsonObject json = this.jsonParser.parse("{ \"key\": -123 }");
    final Integer value = json.digItem("key");
    assertTrue(value == -123);
  }

  @Test
  public void testParseObjectWithPositiveDoubleValue() {
    final JsonObject json = this.jsonParser.parse("{ \"key\": 123.456 }");
    final Double value = json.digItem("key");
    assertTrue(value == 123.456);
  }

  @Test
  public void testParseObjectWithNegativeDoubleValue() {
    final JsonObject json = this.jsonParser.parse("{ \"key\": -123.456 }");
    final Double value = json.digItem("key");
    assertTrue(value == -123.456);
  }

  @Test
  public void testParseObjectWithStringValue() {
    final JsonObject json = this.jsonParser.parse("{ \"key\": \"value\" }");
    assertEquals(json.digItem("key"), "value");
  }

  @Test
  public void testParseObjectWithNullValue() {
    final JsonObject json = this.jsonParser.parse("{ \"key\": null }");
    assertNull(json.digItem("key"));
    assertTrue(json.value().contains("key"));
  }

  @Test
  public void testParseObjectWithObjectValue() {
    final JsonObject json = this.jsonParser.parse("{ \"key\": { \"second key\": \"value\" } }");
    assertEquals(json.digItem("key", "second key"), "value");
  }

  @Test
  public void testParseObjectWithMultipleValues() {
    final JsonObject json = this.jsonParser.parse("{ \"key\": { \"second key\": \"value\" }, \"hi\": 123, \"bye\": null }");
    assertEquals(json.digItem("key", "second key"), "value");
    assertTrue((Integer) json.digItem("hi") == 123);
    assertNull(json.digItem("bye"));
    assertTrue(json.value().contains("bye"));
  }

  @Test
  public void testParseEmptyArray() {
    final JsonArray json = this.jsonParser.parse("[]");
    assertTrue(json.isEmpty());
  }

  @Test
  public void testParseOneElementArray() {
    final JsonArray json = this.jsonParser.parse("[543]");
    assertTrue((Integer) json.getItem(0) == 543);
    assertEquals(json.size(), 1);
  }

  @Test
  public void testParseIntegerElementArray() {
    final JsonArray json = this.jsonParser.parse("[543, 234, 852, -3245]");
    assertTrue((Integer) json.getItem(0) == 543);
    assertTrue((Integer) json.getItem(1) == 234);
    assertTrue((Integer) json.getItem(2) == 852);
    assertTrue((Integer) json.getItem(3) == -3245);
    assertEquals(json.size(), 4);
  }

  @Test
  public void testParseNumberElementArray() {
    final JsonArray json = this.jsonParser.parse("[543, 234.43, -34.852, -3245]");
    assertTrue((Integer) json.getItem(0) == 543);
    assertTrue((Double) json.getItem(1) == 234.43);
    assertTrue((Double) json.getItem(2) == -34.852);
    assertTrue((Integer) json.getItem(3) == -3245);
    assertEquals(json.size(), 4);
  }

  @Test
  public void testParseMultiTypeArray() {
    final JsonArray json = this.jsonParser.parse("[543, 234.43, -34.852, -3245, null, \"hello world\", { \"key\": \"value\" }]");
    assertTrue((Integer) json.getItem(0) == 543);
    assertTrue((Double) json.getItem(1) == 234.43);
    assertTrue((Double) json.getItem(2) == -34.852);
    assertTrue((Integer) json.getItem(3) == -3245);
    assertNull(json.getItem(4));
    assertEquals(json.getItem(5), "hello world");
    final JsonObject object = json.get(6);
    assertEquals(object.digItem("key"), "value");
    assertEquals(json.size(), 7);
  }

  @Test
  public void testParseJsonArrayWithNewLines() {
    final JsonArray json = this.jsonParser.parse("[543,\n234.43,\n-34.852,\n-3245]");
    assertTrue((Integer) json.getItem(0) == 543);
    assertTrue((Double) json.getItem(1) == 234.43);
    assertTrue((Double) json.getItem(2) == -34.852);
    assertTrue((Integer) json.getItem(3) == -3245);
    assertEquals(json.size(), 4);
  }

  @Test
  public void readFromFile() {
    final JsonObject json = new ResourceLoader().loadJson("sample-file");
    assertTrue((Integer) json.digItem("length") == 260);
  }
}
