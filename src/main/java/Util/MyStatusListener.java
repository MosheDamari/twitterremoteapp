package Util;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.json.JSONObject;
import twitter4j.*;


public class MyStatusListener implements StatusListener
{
    private AmazonSQS sqs = null;
    String urlQueue = "";
    String track = "";
    public void onStatus(Status status)
    {

        org.json.JSONObject obj = new JSONObject( TwitterObjectFactory.getRawJSON(status));
        obj.append("Track",this.track);
        String tJson = obj.toString();

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(urlQueue)
                .withMessageBody(tJson)
                .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);

    }

    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    public void onTrackLimitationNotice(int i) {

    }

    public void onScrubGeo(long l, long l1) {

    }

    public void onStallWarning(StallWarning stallWarning) {

    }

    public void onException(Exception e) {

    }

    MyStatusListener(AmazonSQS sqs,String urlQueue,String track)
    {
        this.urlQueue=urlQueue;
        this.sqs = sqs;
        this.track = track;
    }

}
