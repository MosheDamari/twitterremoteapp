package DB;

import Objects.TwitterObj;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Abstraction layer for database access
 */
public class DataStorage {
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
  public void addLink(TwitterObj twitt)
  {

    // create a sql date object so we can use it in our INSERT statement
    Calendar calendar = Calendar.getInstance();
    java.sql.Date date_created = new java.sql.Date(calendar.getTime().getTime());

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
