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
    setOAuthConsumerKey = "";
    setOAuthConsumerSecret = "";
    setOAuthAccessToken = "";
    setOAuthAccessTokenSecret = "";

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
