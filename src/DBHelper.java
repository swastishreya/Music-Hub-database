import java.sql.*;
import java.util.Scanner;

public class DBHelper {
   //Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/music_hub_db";

   //Database credentials
   static final String USER = "root";
   static final String PASS = "Swasti123#Shreya";
   
   static Connection conn = null;
   static Statement stmt = null;
   static Scanner sc = new Scanner(System.in);

   public void initialize() {
      try {
         // Register JDBC driver
         Class.forName(JDBC_DRIVER);

         // Open a connection
         System.out.println("Connecting to database...");
         conn = DriverManager.getConnection(DB_URL, USER, PASS);

         //Create a statement
         System.out.println("Creating statement...");
         stmt = conn.createStatement();

      }catch(SQLException se){
         //Handle errors for JDBC
         se.printStackTrace();
      }catch(Exception e){
         //Handle errors for Class.forName
         e.printStackTrace();
      }
   }

   public void stop() {
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }finally{
         System.out.println("Goodbye!");
      }
   }

   public User login(String login_id){
      System.out.println("Enter password:");
      String password = sc.nextLine();

      try{

         String sql;
         sql = "select * from user where login_id = '" + login_id + "'";
         ResultSet rs = stmt.executeQuery(sql);
         User user = null;

         while(rs.next()){
            String password_retrived = rs.getString("password");
            System.out.println(password_retrived);

            if(password_retrived.equals(password)){
               user = new User();
               user.setLogin_id(rs.getString("login_id"));
               user.setPassword(rs.getString("password"));
               user.setUser_id(rs.getInt("user_id"));
               user.setUser_name(rs.getString("user_name"));
               if(rs.getInt("is_artist") == 1)
                  user.setIs_artist(true);
               else if(rs.getInt("is_artist") == 0)
                  user.setIs_artist(false);
            }
         }
         rs.close();
         return user;
      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
      return null;
   }

   public int getNumOf(String table){
      int num = 0;
      try{
         String sql;
         sql = "select count(*) from " + table;
         ResultSet rs = stmt.executeQuery(sql);

         while(rs.next()){
            num = rs.getInt("count(*)");
         }
         rs.close();
         return num;
      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
      return num;
   }

   public void playSong(User user){
      try{
         String sql;
         sql = "select queue_list from queue where queue_id = " + String.valueOf(user.getUser_id());
         ResultSet rs = stmt.executeQuery(sql);

         while(rs.next()){
            String[] song_list = rs.getString("queue_list").split(",");
            System.out.println("Currently playing: " + song_list[0]);
         }
         rs.close();
      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
   }

   public void createPlaylist(User user){
      try{
         String sql;
         Playlist p = new Playlist();
         System.out.println("Enter playlist name: ");
         String playlistName = sc.nextLine();
         p.setPlaylist_name(playlistName);
         p.setP_user_id(user.getUser_id());
         p.setIs_downloaded(false);
         System.out.println("Enter song name: ");
         String songName = sc.nextLine();
         int song_id = fetchSongId(songName);
         p.setP_song_id(song_id);
         sql = "insert into playlist values (" + String.valueOf(p.getPlaylistId()) + ", '" + playlistName + "', FALSE," + String.valueOf(user.getUser_id()) + "," + String.valueOf(p.getP_song_id()) +")";
         stmt.executeUpdate(sql);

      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
   }

   public int fetchSongId(String songName){
      int song_id = -1;
      try{
         String sql;
         sql = "select song_id from song where song_name = '" + songName + "'";
         ResultSet rs = stmt.executeQuery(sql);

         while(rs.next()){
            song_id = rs.getInt("song_id");
         }
         rs.close();
         return song_id;
      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
      return song_id;
   }

   public void downloadCurrentSong(User user){
      try{
         String currentSong = "";
         String[] song_list;
         String sql, sql2;
         sql = "select queue_list from queue where queue_id = " + String.valueOf(user.getUser_id());
         ResultSet rs = stmt.executeQuery(sql);

         while(rs.next()){
            song_list = rs.getString("queue_list").split(",");
            currentSong = song_list[0];
         }
         if(currentSong.equals(""))
            System.out.println("There's no song in the queue to download!");
         else{
            int song_id = fetchSongId(currentSong);
            sql2 = "update playlist set is_downloaded = TRUE where p_user_id = " + String.valueOf(user.getUser_id()) + " and p_song_id = " + String.valueOf(song_id);
            stmt.executeUpdate(sql2);
         }
         rs.close();
      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
   }

   public void addSongToQueue(User user){
      try{
         String song_list = null, addSong, new_song_list;
         String sql, sql2;

         System.out.println("Enter song name: ");
         addSong = sc.nextLine();

         sql = "select queue_list from queue where queue_id = " + String.valueOf(user.getUser_id());
         ResultSet rs = stmt.executeQuery(sql);

         while(rs.next()){
            song_list = rs.getString("queue_list");
         } 
         if(song_list == null){
            new_song_list = addSong;
            sql2 = "insert into queue values (" + String.valueOf(user.getUser_id()) + ", '" + new_song_list + "', FALSE)";
         }
         else {
            new_song_list = song_list + "," + addSong;
            sql2 = "update queue set queue_list = '" + new_song_list + "' where queue_id = " + String.valueOf(user.getUser_id());
         }
         stmt.executeUpdate(sql2);

         rs.close();
      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
   }

   public void uploadSong(User user){
      try{
         String sql;
         System.out.println("Enter song name: ");
         String songName = sc.nextLine();
         Song s = new Song();
         s.setSong_name(songName);
         System.out.println(user.isIs_artist());
         if(user.isIs_artist() == true){
            sql = "insert into song values (" + String.valueOf(s.getSong_id()) + ", '" + s.getSong_name() + "'," + String.valueOf(user.getUser_id()) + ")";
            stmt.executeUpdate(sql);
         }
         else
            System.out.println("Hey! You're not a verified artist yet!");
         

      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
   }

   public void shufflePlay(User user){
      try{
         String sql;
         sql = "update queue set is_shuffle_play = TRUE where queue_id = " + String.valueOf(user.getUser_id());
         stmt.executeUpdate(sql);

      }catch(SQLException se){
         se.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
   }
}