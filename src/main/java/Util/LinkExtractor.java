package Util;

import Objects.TwitterObj;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.URLEntity;

import java.io.IOException;
import java.util.List;

/**
 * Extract content from links
 */
public class LinkExtractor {
  public static void main(String[] args)
  {
    AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion("eu-west-1").build();
    while(true) {
      List<Message> messages = sqs.receiveMessage(args[4]).getMessages();
      for (Message m : messages) {
        try {
          Status status = TwitterObjectFactory.createStatus(m.getBody());
          TwitterObj obj = extractContent(status);
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
        } catch (TwitterException e) {
          e.printStackTrace();
        }
      }
    }
  }
  public static TwitterObj extractContent(Status status)
  {
    ScreenshotGenerator gen = new ScreenshotGenerator();
    Document doc = null;
    TwitterObj myObj = null;
    URLEntity[] urlEntitys = status.getURLEntities();
    for (URLEntity url: urlEntitys)
    {
    try {
      doc = Jsoup.connect(url.getExpandedURL()).get();
      myObj = new TwitterObj(status.getUser().getName(),url.getExpandedURL(),doc.text(),url.getExpandedURL(),doc.title(),"");

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
    }
    return myObj;
  }
}
