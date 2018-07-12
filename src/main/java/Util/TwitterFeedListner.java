package Util;

import DB.DataStorage;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterFeedListner
{
    // Create our configuration
    private DataStorage storage;

    private ConfigurationBuilder cb;
    TwitterStreamFactory tf;
    TwitterStream twitterStream;
    MyStatusListener listener;
    public static void main(String[] args)
    {
        String setOAuthConsumerKey, setOAuthConsumerSecret, setOAuthAccessToken,setOAuthAccessTokenSecret;
        setOAuthConsumerKey = args[0];
        setOAuthConsumerSecret = args[1];
        setOAuthAccessToken = args[2];
        setOAuthAccessTokenSecret = args[3];
        String urlQueue = args[4];
        AmazonSQS sqs  = AmazonSQSClientBuilder.standard().withRegion("eu-west-1").withCredentials(new EnvironmentVariableCredentialsProvider()).build();
        TwitterFeedListner mosheTwitter = new TwitterFeedListner(setOAuthConsumerKey ,setOAuthConsumerSecret, setOAuthAccessToken, setOAuthAccessTokenSecret ,sqs,urlQueue);
        mosheTwitter.Listen(args[5]);
    }

    public TwitterFeedListner(String setOAuthConsumerKey, String setOAuthConsumerSecret, String setOAuthAccessToken, String setOAuthAccessTokenSecret, AmazonSQS sqs , String urlQueue)
    {
        cb = new ConfigurationBuilder()
                .setOAuthConsumerKey(setOAuthConsumerKey)
                .setOAuthConsumerSecret(setOAuthConsumerSecret)
                .setOAuthAccessToken(setOAuthAccessToken)
                .setOAuthAccessTokenSecret(setOAuthAccessTokenSecret)
                .setJSONStoreEnabled(true);

        tf = new TwitterStreamFactory(cb.build());
        twitterStream = tf.getInstance();

        listener = new MyStatusListener(sqs,urlQueue);
        twitterStream.addListener(listener);
    }

    public void Listen(String filter)
    {
        twitterStream.filter(filter);
    }

}
