package Main;

import DB.DataStorage;
import Util.LinkExtractor;
import Util.TwitterFeedListner;
import javafx.scene.chart.PieChart;

public class Main {
  public static void main(String[] args)
  {
    String[] param = new String[6];
    param[0] = "SB9XIaVYkQeMGIRSb2JCE6mjo";
    param[1] = "gTdUdKnCu8h0A4peHAri10703AxcoTxRHTh9MztuC81Qyi1NvV";
    param[2] = "1280908806-e2PyK8ly1o0QqUB0pvwdyfVYd5x1EtutKtjQkIN";
    param[3] = "jCWukDcXIWyKGT9MuUVFLB75lte6LHW3XKLFgFimf8655";
    param[4] = "https://sqs.eu-west-1.amazonaws.com/532798212738/queuetwitter";
    param[5] = "https://sqs.eu-west-1.amazonaws.com/532798212738/dbqueue";


    String[] param1 = new String[6];
    param1[0] = "twitterObj";
    param1[1] = "moshe";
    param1[2] = "Moshe123";
    param1[3] = "twitterobj.ceet07vpbjz2.eu-west-1.rds.amazonaws.com";
    param1[4] = "3306";
    param1[5] = "https://sqs.eu-west-1.amazonaws.com/532798212738/dbqueue";

    //TwitterFeedListner.main(param);
    //LinkExtractor.main(param);
    DataStorage.main(param1);
  }
}
