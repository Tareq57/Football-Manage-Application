package sample.server;

import sample.Action_club_country_player.Action;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;
import sample.util.NetworkUtil;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Server {

    private ServerSocket serverSocket;
    public  static HashMap<String, String> passwordMap=new HashMap<>();
    public static ArrayList<Club> clubs=new ArrayList<>();
    public  static ArrayList<Player> players;
    public  HashMap<String,ClientInfo> clientMap;
    Server() {
        clientMap=new HashMap<>();
        try {
            serverSocket = new ServerSocket(55555);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReadThreadServer(passwordMap,clubs,players,networkUtil,clientMap);
    }

    public static void main(String[] args) {
        Databse db=new Databse();
        ArrayList<String> list= db.getPasswordList();
        for(String str:list)
        {
            String[] password=str.split(",");
            passwordMap.put(password[0].toUpperCase(),password[1]);
        }

        try {
            ArrayList<Player> playerArrayList=db.getPlayerList("player_database");
            players= db.getPlayerList("UnSoldPlayer");
            clubs=Action.Add_in_Club(playerArrayList);
        } catch (SQLException exception) {
            System.out.println(exception);
        }


        new Server();
    }
}
