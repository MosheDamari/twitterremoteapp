package Util;

import Objects.TwitterObj;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import twitter4j.Status;
import twitter4j.URLEntity;

import java.io.IOException;

/**
 * Extract content from links
 */
public class LinkExtractor {
  public TwitterObj extractContent(URLEntity url, Status status)
  {
    ScreenshotGenerator gen = new ScreenshotGenerator();
    Document doc = null;
    TwitterObj myObj = null;

    try {
      doc = Jsoup.connect(url.getExpandedURL()).get();
      myObj = new TwitterObj(status.getUser().getName(),url.getExpandedURL(),doc.text(),gen.takeScreenshot(url.getExpandedURL()),doc.title(),"");

    } catch (IOException e) {
      e.printStackTrace();
    }
    Elements metaTag =  doc.getElementsByTag("meta");
    for (Element tag:metaTag)
    {
      String name = tag.attr("name");
      String cont = tag.attr("content");
      if(name.endsWith(":description"))
      {
        myObj.setDisc(cont);
      }
    }
    return myObj;
  }
}
