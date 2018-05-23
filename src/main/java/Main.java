import DB.DataStorage;
import Util.TwitterFeedListner;

import java.sql.SQLException;

public class Main {
  public static void main(String[] args)
  {
    DataStorage storage = null;
    try {
       storage = new DataStorage();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    String setOAuthConsumerKey, setOAuthConsumerSecret, setOAuthAccessToken,setOAuthAccessTokenSecret;
    setOAuthConsumerKey = "zmdHtwGBSGaxxC6KHifnvTmrb";
    setOAuthConsumerSecret = "JI9Q7IFc7K5f2Lr7Ync1BEpmmtplmjfqtb6RR64fFV2eKuY3ws";
    setOAuthAccessToken = "1280908806-e2PyK8ly1o0QqUB0pvwdyfVYd5x1EtutKtjQkIN";
    setOAuthAccessTokenSecret = "jCWukDcXIWyKGT9MuUVFLB75lte6LHW3XKLFgFimf8655";

    TwitterFeedListner mosheTwitter = new TwitterFeedListner(setOAuthConsumerKey ,setOAuthConsumerSecret, setOAuthAccessToken, setOAuthAccessTokenSecret , storage);
    mosheTwitter.Listen();
/*
    ArrayList<String> mydb =  storage.ReadMySql();
    for (String str : mydb)
    {

      System.out.println(str);
    }
*/
  }
}
