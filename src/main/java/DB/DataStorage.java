package DB;

import Objects.TwitterObj;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;


import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Abstraction layer for database access
 */
public class DataStorage {


  public static void main(String[] args)
  {
    Connection con = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String dbName = args[0];
      String userName = args[1];
      String password = args[2];
      String hostname = args[3];
      String port = args[4];
      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
      con = DriverManager.getConnection(jdbcUrl);
      Statement statement = con.createStatement();
      String twitTable = "CREATE TABLE twitTable " +
              "(id INTEGER not NULL , " +
              " name VARCHAR(256), " +
              " title VARCHAR(256), " +
              " link VARCHAR(256), " +
              " content VARCHAR(256), " +
              " image VARCHAR(256), " +
              " disc VARCHAR(256), " +
              " date_created DATE, " +
              " PRIMARY KEY ( id ))";
      statement.executeUpdate(twitTable);

    }
    catch (ClassNotFoundException e) { System.out.println(e);}
    catch (SQLException e) {System.out.println(e);}

    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    while(true) {
      List<Message> messages = sqs.receiveMessage(args[5]).getMessages();
      for (Message m : messages)
      {
        AddLink(new TwitterObj(m.getBody()),con);
        sqs.deleteMessage(args[5], m.getReceiptHandle());
      }
    }

  }
  Connection conn;
  int count = 1,newID = 1;

  public DataStorage() throws SQLException {
    this("twitterlinks.db");
  }

  DataStorage(String database) throws SQLException {
    String url = "jdbc:sqlite:" + database;
    conn = DriverManager.getConnection(url);
    Statement statement = conn.createStatement();

    //The next line has the issue
    String twitTable = "CREATE TABLE twitTable " +
            "(id INTEGER not NULL , " +
            " name VARCHAR(20), " +
            " title VARCHAR(20), " +
            " link VARCHAR(20), " +
            " content VARCHAR(20), " +
            " image VARCHAR(20), " +
            " disc VARCHAR(20), " +
            " date_created DATE, " +
            " PRIMARY KEY ( id ))";
    statement.executeUpdate(twitTable);

  }

  /**
   * Add link to the database
   */
  public static void  AddLink(TwitterObj twitt,Connection con)
  {
    Connection conn = con;
    // create a sql date object so we can use it in our INSERT statement
    Calendar calendar = Calendar.getInstance();
    java.sql.Date date_created = new java.sql.Date(calendar.getTime().getTime());
    int count =0;
    int newID = 0 ;
    // create the mysql insert preparedstatement
    try {

      if (count - 1000 < 0)
      {
        String query = " insert into twitTable (id,name, title, link, content,image,disc,date_created)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, count);
        preparedStmt.setString(2, twitt.getName());
        preparedStmt.setString(3, twitt.getTitle());
        preparedStmt.setString(4, twitt.getLink());
        preparedStmt.setString(5, twitt.getContent());
        preparedStmt.setString(6, twitt.getImage());
        preparedStmt.setString(7, twitt.getDisc());
        preparedStmt.setDate(8, date_created);
        preparedStmt.execute();
        count++;
      }
      else
      {
        newID = count % 1000 ;
        System.out.println(newID);
        PreparedStatement preparedStmt = conn.prepareStatement("update twitTable set name = ?, title = ?, link = ?, content = ?, image = ?, disc = ?, date_crated = ?  where id = ?");
        preparedStmt.setString(1, twitt.getName());
        preparedStmt.setString(3, twitt.getLink());
        preparedStmt.setString(2, twitt.getTitle());
        preparedStmt.setString(4, twitt.getContent());
        preparedStmt.setString(5, twitt.getImage());
        preparedStmt.setString(6, twitt.getDisc());
        preparedStmt.setDate(7, date_created);
        preparedStmt.executeUpdate();
        count++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
  public TwitterObj[] ReadMySql(int amount)
  {
    TwitterObj[] objs = new TwitterObj[amount];
    ArrayList<String> tableLines = new ArrayList<String>();
    String query = "SELECT * FROM twitTable";
    try {
      Statement st = conn.createStatement();
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      // iterate through the java resultset
      while (rs.next() && amount > 0)
      {
        objs[amount-1] = new TwitterObj(rs.getString("name"),
                rs.getString("link"),
                rs.getString("content"),
                rs.getString("image"),
                rs.getString("title"),
                rs.getString("disc"));
        amount--;
      }
      st.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }


    return objs;
  }
}