package titaniumassetgenerator.utils.json;

import titaniumassetgenerator.utils.SimpleMap;
import titaniumassetgenerator.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonParser {

  public <T extends Json> T parse(final String json) {
    final Tuple parseInfo = this.parseNextItem(json);
    final T jsonValue = parseInfo.get(0);
    final String remainder = parseInfo.get(1);

    if (remainder.isEmpty()) {
      return jsonValue;
    }

    throw new RuntimeException("Unable to parse provided JSON - Provided String appears to contain multiple JSONs");
  }

  private Tuple parseNextItem(String json) {
    json = this.trim(json);

    final char first = json.charAt(0);

    if (first == '-' || (first >= '0' && first <= '9')) {
      return this.parseJsonNumber(json);
    } else if (first == 'n') {
      return this.parseNull(json);
    } else if (first == 'f' || first == 't') {
      return this.parseBoolean(json);
    } else if (first == '"') {
      return this.parseJsonString(json);
    } else if (first == '[') {
      return this.parseJsonArray(json);
    } else if (first == '{') {
      return this.parseJsonObject(json);
    }

    throw new RuntimeException("Unable to parse provided JSON - does not match any known format");
  }

  private Tuple parseNull(String json) {
    json = this.trim(json);
    if (!json.startsWith("null")) {
      throw new RuntimeException("Expected null");
    }

    return new Tuple(new Json(null), json.substring(4));
  }

  private Tuple parseBoolean(String json) {
    json = this.trim(json);
    if (json.startsWith("true")) {
      return new Tuple(new Json<>(true), json.substring(4));
    } else if (json.startsWith("false")) {
      return new Tuple(new Json<>(false), json.substring(5));
    }

    throw new RuntimeException("Expected boolean");
  }

  private Tuple parseJsonNumber(String json) {
    json = this.trim(json);

    final StringBuilder numberBuffer = new StringBuilder();
    int index = 0;

    if (json.charAt(0) == '-') {
      numberBuffer.append('-');
      index++;
    }

    if (json.charAt(index) < '0' || json.charAt(index) > '9') {
      throw new RuntimeException("Expected number");
    }

    boolean hasSeenDecimal = false;
    while (index < json.length()) {
      final char current = json.charAt(index++);
      if ((current < '0' || current > '9') && current != '.') {
        index--;
        break;
      }

      if (current == '.') {
        if (hasSeenDecimal) {
          throw new RuntimeException("Found number with more than one decimal point");
        }

        hasSeenDecimal = true;
      }

      numberBuffer.append(current);
    }

    final String value = numberBuffer.toString();
    json = json.substring(index);

    if (value.contains(".")) {
      return new Tuple(new Json<>(Double.parseDouble(value)), json);
    }

    return new Tuple(new Json<>(Integer.parseInt(value)), json);
  }

  private Tuple parseJsonString(String json) {
    json = this.trim(json);
    if (json.charAt(0) != '"') {
      throw new RuntimeException("Expected String but couldn't find quote");
    }

    final StringBuilder value = new StringBuilder();
    int index = 1; // ignore the leading quotation
    while (index < json.length()) {
      final char current = json.charAt(index);

      if (current == '"' && json.charAt(index - 1) != '\\') {
        return new Tuple(new Json<>(value.toString()), json.substring(index + 1));
      }

      value.append(current);
      index++;
    }

    throw new RuntimeException("String does not have closing quote");
  }

  private Tuple parseJsonArray(String json) {
    json = this.trim(json);
    if (json.charAt(0) != '[') {
      throw new RuntimeException("Expected array");
    }
    // Remove the open bracket
    json = json.substring(1);

    final List<Json<?>> values = new ArrayList<>();
    while (!json.isEmpty()) {
      json = this.trim(json);

      if (json.charAt(0) == ']') {
        return new Tuple(new JsonArray(values), json.substring(1));
      }

      final Tuple nextElementInfo = this.parseNextItem(json);
      values.add(nextElementInfo.get(0));
      json = nextElementInfo.get(1);

      json = this.trim(json);
      if (json.charAt(0) == ',') {
        json = json.substring(1);
      }
    }

    throw new RuntimeException("Unclosed array");
  }

  private Tuple parseJsonObject(String json) {
    json = this.trim(json);
    if (json.charAt(0) != '{') {
      throw new RuntimeException("Expected object");
    }
    json = json.substring(1);

    final SimpleMap values = new SimpleMap();

    while (!json.isEmpty()) {
      json = this.trim(json);

      if (json.charAt(0) == '}') {
        return new Tuple(new JsonObject(values), json.substring(1));
      }

      final Tuple keyInfo = this.findNextObjectKey(json);
      final String key = keyInfo.get(0);
      json = keyInfo.get(1);

      final Tuple parseInfo = this.parseNextItem(json);
      final Json parsedValue = parseInfo.get(0);
      json = parseInfo.get(1);
      values.put(key, parsedValue);

      json = this.trim(json);
      if (json.charAt(0) == ',') {
        json = json.substring(1);
      }
    }

    throw new RuntimeException("Incomplete JSON Object found");
  }

  private Tuple findNextObjectKey(String json) {
    json = this.trim(json);

    if (json.charAt(0) != '"') {
      throw new RuntimeException("Invalid JSON object, couldn't locate next key");
    }

    final StringBuilder objectKey = new StringBuilder();
    int index = 1;
    while (index < json.length()) {
      final char current = json.charAt(index);
      final char previous = json.charAt(index - 1);
      index++;

      if (current == '"' && previous != '\\') {
        break;
      }

      objectKey.append(current);
    }

    if (index == json.length()) {
      throw new RuntimeException("Found key that maps to no value");
    } else if (json.charAt(index) != ':') {
      throw new RuntimeException("Found key that doesn't complete with a colon");
    }

    return new Tuple(objectKey.toString(), json.substring(index + 1));
  }

  private static final Set<Character> WHITESPACE_CHARACTERS = new HashSet<>(Arrays.asList(' ', '\n', '\t', '\r'));
  private String trim(String value) {
    int index = 0;

    while (index < value.length()) {
      final char current = value.charAt(index);
      if (!WHITESPACE_CHARACTERS.contains(current)) {
        break;
      }

      index++;
    }

    return value.substring(index);
  }
}
