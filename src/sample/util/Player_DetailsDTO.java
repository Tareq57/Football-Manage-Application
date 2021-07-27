package sample.util;

import javafx.application.Platform;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;

import java.io.Serializable;

public class Player_DetailsDTO implements Serializable {
   Player player;
   String club_name;
   Club club;
   String status;

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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
