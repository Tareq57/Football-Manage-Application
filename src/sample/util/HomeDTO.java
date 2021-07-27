package sample.util;

import sample.Action_club_country_player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeDTO implements Serializable {
    String club_name;
    ArrayList<Player> players;

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
