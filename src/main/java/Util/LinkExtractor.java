package Util;

import Objects.TwitterObj;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Extract content from links
 */
public class LinkExtractor {

  public static void main(String[] args)
  {
    ConfigurationBuilder cb;
    AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion("eu-west-1").build();
    while(true) {
      List<Message> messages = sqs.receiveMessage(args[0]).getMessages();
      for (Message m : messages) {
          org.json.JSONObject jObj2 = new JSONObject(m.getBody());
          try {
            TwitterObj obj = extractContent(jObj2);
          if (obj == null) {
            sqs.deleteMessage(args[0], m.getReceiptHandle());
          }
          else
          {
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(args[1])
                    .withMessageBody(obj.ToJson().toString())
                    .withDelaySeconds(5);
            sqs.sendMessage(send_msg_request);
            sqs.deleteMessage(args[0], m.getReceiptHandle());
          }
          }
          catch(IOException e)
          {
            sqs.deleteMessage(args[0], m.getReceiptHandle());
          }
        }
      }
    }

  public static TwitterObj extractContent(org.json.JSONObject obj) throws IOException {
    ScreenshotGenerator gen = new ScreenshotGenerator();
    Document doc = null;
    TwitterObj myObj = null;
    Status status = null;
    try {
      status = TwitterObjectFactory.createStatus(obj.toString());
    } catch (TwitterException e) {
      e.printStackTrace();
    }
    URLEntity[] urlEntitys = status.getURLEntities();
    for (URLEntity url: urlEntitys)
    {

      doc = Jsoup.connect(url.getExpandedURL()).get();
      myObj = new TwitterObj(status.getUser().getName(),url.getExpandedURL(),doc.text(),url.getExpandedURL(),doc.title(),"",obj.get("Track").toString());

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
    }
    return myObj;
  }
}
