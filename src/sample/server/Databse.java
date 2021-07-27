package sample.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Action_club_country_player.Player;

import java.sql.*;
import java.util.ArrayList;

public class Databse {
    Connection getConnection() throws SQLException {
        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/new","root","");
        return  conn;
    }
        ArrayList<Player> getPlayerList(String str) throws SQLException {
        ArrayList<Player> playerlist=new ArrayList<>();
        Connection conn=getConnection();
        Statement statement=conn.createStatement();
        String query ="SELECT * FROM "+str;
        ResultSet rs =statement.executeQuery(query);
        while (rs.next()) {

            String name=rs.getString("Name");
            String country=rs.getString("Country");
            String age=rs.getString("Age");
            String height=rs.getString("Height");
            String club=rs.getString("Club");
            String position=rs.getString("Position");
            String number=rs.getString("Number");
            String weeklysalary=rs.getString("WeeklySalary");
            String img=rs.getString("image");
            String price=rs.getString("Price");
            Player player=new Player(name,country,Integer.parseInt(age),Double.parseDouble(height),club,position,Integer.parseInt(number),Double.parseDouble(weeklysalary),img,Double.parseDouble(price));
            playerlist.add(player);

        }
        return  playerlist;
    }
    ArrayList<String> getPasswordList(){
        ArrayList<String> list=new ArrayList<>();
        Connection conn= null;
        try {
            conn = getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        Statement statement= null;
        try {
            statement = conn.createStatement();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        String query ="SELECT * FROM passwordlist";
        try {
            ResultSet rs =statement.executeQuery(query);
            while (rs.next())
            {
                String str=rs.getString("Club_Name")+","+rs.getString("Password");
                list.add(str);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;

    }
    void add(Player player,String str) throws SQLException {
        Connection conn=getConnection();
        String sql = "insert into "+str+" "
                + " (Name, Country, Age,Height,Club,Position,Number,WeeklySalary,image,Price)" + " values (?, ?, ?,?,?,?,?,?,?,?)";

        PreparedStatement statement = conn.prepareStatement(sql);


        // set param values
        statement.setString(1, player.getName());
        statement.setString(2, player.getCountry());
        statement.setString(3, Integer.toString(player.getAge()));
        statement.setString(4, Double.toString(player.getHeight()));
        statement.setString(5, player.getClub());
        statement.setString(6, player.getPosition());
        statement.setString(7, Integer.toString(player.getNumber()));
        statement.setString(8, Double.toString(player.getWeeklySalary()));
        statement.setString(9, player.getImg());
        statement.setString(10, Double.toString(player.getSell_price()));
        // 3. Execute SQL query
        statement.executeUpdate();
    }
    void delete(Player player,String str) throws SQLException {
        Connection conn=getConnection();
        String sql ="delete from "+str+" where Name = ?";
        PreparedStatement statement=conn.prepareStatement(sql);
        statement.setString(1,player.getName());
        statement.executeUpdate();
    }


}
