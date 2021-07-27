package sample.util;

import sample.Action_club_country_player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginDTO implements Serializable {
    private String userName;
    private String password;
    private boolean status;
    private ArrayList<Player> players;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


}
