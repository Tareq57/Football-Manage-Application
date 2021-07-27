package sample.util;

import sample.Action_club_country_player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class BuyReqDTO implements Serializable {
    Player player;
    String club_name;
    ArrayList<Player> playerArrayList;

    public ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }
}
