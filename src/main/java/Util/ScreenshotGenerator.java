package Util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ScreenshotGenerator {
  public String takeScreenshot(String url) {
    String screenshotFilePath = null;

    try {
      Runtime.getRuntime().exec("C:\\Users\\Admin\\twitterremoteapp\\src\\main\\resources\\SlimerJSScreenshot " + url + " " + url+".png");
    } catch (IOException e) {
      e.printStackTrace();
    }

    AmazonS3 client = AmazonS3ClientBuilder.defaultClient();
    // Upload a file
    client.putObject("BUCKET", "KEY", new File("/path/to/file.png"));
    // Get the object URL
    URL s3Url = client.getUrl("BUCKET", "KEY");


    return s3Url.toString();
  }
}
