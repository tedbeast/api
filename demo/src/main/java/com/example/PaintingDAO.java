package com.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (data-access-object) is a style of a class that is designed to handle database interactions
 */
public class PaintingDAO {
    Connection conn;
    public PaintingDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:h2:./h2/db");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * for testing - i can drop my painting table
     */
    public void dropPaintingTable(){
        try{
            PreparedStatement ps = conn.prepareStatement("drop table painting");
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * for testing - i can create my painting table
     */
    public void createPaintingTable(){
        try{
            PreparedStatement ps = conn.prepareStatement("create table painting(title varchar(255), year_made int)");
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * JDBC logic to insert a painting into my h2 database
     * @param painting
     */
    public void insertPainting(Painting painting){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into painting (title, year_made) values (?, ?)");
//            ps.setString, ps.setInt 'fills in' the question marks in a preparedStatement
//            this not only makes them easier to write but it also avoids a security issue called SQL injection
            ps.setString(1, painting.title);
            ps.setInt(2, painting.year_made);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * JDBC logic to get all my paintings from painting table
     * @return
     */
    public List<Painting> getAllPainting(){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from painting");
//            because we want to retrieve data meaningfully in java, we have to expect data in the form of a 'resultset'
//            we also have to use executeQuery instead of executeUpdate, because executeQuery is expecting a resultSet
            ResultSet rs = ps.executeQuery();
            List<Painting> allPaintings = new ArrayList<>();
//            we have to loop through the entire resultset for every item it contains
            while(rs.next()){
//                we have to extract the DB column of each row into a meaningful java object
                Painting newPainting = new Painting(rs.getString("title"), rs.getInt("year_made"));
                allPaintings.add(newPainting);
            }
            return allPaintings;

        }catch(SQLException e){
            e.printStackTrace();
        }
//        in the event that we don't get to return allPaintings because a SQLException was thrown, just return null
        return null;
    }

    /**
     * using the preparedstatement's setInt (or setString etc) methods to set unknown parameters of a SQL query.
     * @param year
     * @return
     */
    public List<Painting> getAllPaintingMadeInYear(int year){
        try{
//            we could also write:
//            PreparedStatement ps = conn.prepareStatement("select * from painting where year_made = "+year+");
            PreparedStatement ps = conn.prepareStatement("select * from painting where year_made = ?");
//            because we want to retrieve data meaningfully in java, we have to expect data in the form of a 'resultset'
//            we also have to use executeQuery instead of executeUpdate, because executeQuery is expecting a resultSet
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            List<Painting> allPaintings = new ArrayList<>();
//            we have to loop through the entire resultset for every item it contains
            while(rs.next()){
//                we have to extract the DB column of each row into a meaningful java object
                Painting newPainting = new Painting(rs.getString("title"), rs.getInt("year_made"));
                allPaintings.add(newPainting);
            }
            return allPaintings;

        }catch(SQLException e){
            e.printStackTrace();
        }
//        in the event that we don't get to return allPaintings because a SQLException was thrown, just return null
        return null;
    }

    /**
     * of all the paintings in the painting table, return the oldest painting's year.
     *
     * @return select min(year) as oldest_year from painting;
     */
    public int getOldestPaintingYear(){
        try{
            PreparedStatement ps = conn.prepareStatement("select min(year_made) as oldest_year from painting");
            ResultSet rs = ps.executeQuery();
//            if the resultset has a value, do this:
//            (if there are no values in the resultset, we'll skip the block of code and return 0...)
            if(rs.next()){
                int oldestYear = rs.getInt("oldest_year");
                return oldestYear;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
