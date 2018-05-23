package Util;


import DB.DataStorage;
import Objects.TwitterObj;
import Util.LinkExtractor;
import twitter4j.*;

public class MyStatusListener implements StatusListener
{
    private DataStorage db = null;
    public void onStatus(Status status)
    {
        LinkExtractor extract = new LinkExtractor();
        URLEntity[] urls = status.getURLEntities();

        if (urls.length > 0)
        {
            for (URLEntity url : urls) {

                db.addLink(extract.extractContent(url,status));
            }
        }
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

    MyStatusListener(DataStorage store)
    {
        this.db = store;
    }

}
