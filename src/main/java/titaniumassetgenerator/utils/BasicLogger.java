package titaniumassetgenerator.utils;

import java.util.ResourceBundle;

public class BasicLogger implements System.Logger {
  private static BasicLogger singleton;

  public static BasicLogger logger() {
    if (singleton == null) {
      singleton = new BasicLogger();
    }

    return singleton;
  }

  private BasicLogger() {} // No Op

  @Override
  public String getName() {
    return "TitaniumAssetGenerator";
  }

  @Override
  public boolean isLoggable(Level level) {
    return true;
  }

  @Override
  public void log(Level level, ResourceBundle resourceBundle, String s, Throwable throwable) {
    StringBuffer messageBuffer = new StringBuffer();
    messageBuffer.append(s);
    messageBuffer.append(" - ");
    messageBuffer.append(throwable.getClass().getName());

    for (StackTraceElement ele : throwable.getStackTrace()) {
      messageBuffer.append("\n");
      messageBuffer.append(ele.getFileName());
      messageBuffer.append(":Line=");
      messageBuffer.append(ele.getLineNumber());
      messageBuffer.append(": ");
    }

    System.out.println(messageBuffer.toString());
  }

  @Override
  public void log(Level level, ResourceBundle resourceBundle, String s, Object... objects) {
    StringBuffer messageBuffer = new StringBuffer();
    messageBuffer.append(s);

    for (Object object : objects) {
      messageBuffer.append("; ");
      messageBuffer.append(object.toString());
    }

    System.out.println(messageBuffer.toString());
  }
}
