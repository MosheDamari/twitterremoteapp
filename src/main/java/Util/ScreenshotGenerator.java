package Util;

import java.io.IOException;

public class ScreenshotGenerator {
  public String takeScreenshot(String url) {
    String screenshotFilePath = null;

    try {
      Runtime.getRuntime().exec("C:\\Users\\Admin\\twitterremoteapp\\src\\main\\resources\\SlimerJSScreenshot " + url + " " + url+".png");
    } catch (IOException e) {
      e.printStackTrace();
    }

    return url+".png";
  }
}
