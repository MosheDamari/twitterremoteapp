package Util;

import DB.DataStorage;
import Util.MyStatusListener;
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


    public TwitterFeedListner(String setOAuthConsumerKey, String setOAuthConsumerSecret, String setOAuthAccessToken, String setOAuthAccessTokenSecret, DataStorage store)
    {
        cb = new ConfigurationBuilder()
                .setOAuthConsumerKey(setOAuthConsumerKey)
                .setOAuthConsumerSecret(setOAuthConsumerSecret)
                .setOAuthAccessToken(setOAuthAccessToken)
                .setOAuthAccessTokenSecret(setOAuthAccessTokenSecret);

        tf = new TwitterStreamFactory(cb.build());
        twitterStream = tf.getInstance();

        listener = new MyStatusListener(store);
        twitterStream.addListener(listener);
    }

    public void Listen()
    {
    twitterStream.sample();
    }

}
