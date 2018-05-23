package Util;

import java.io.IOException;

public class ScreenshotGenerator {
  public String takeScreenshot(String url) {
    String screenshotFilePath = null;

    try {
      Runtime.getRuntime().exec("/Users/moshedamari/Icloud/Cloud/src/main/resources/SlimerJSScreenshot/slimerjs-1.0.0/slimerjs --headless screenshot.js " + url + " " + c+".png");
    } catch (IOException e) {
      e.printStackTrace();
    }

    return url+".png";
  }
}
