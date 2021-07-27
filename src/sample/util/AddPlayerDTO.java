package sample.util;

import javafx.application.Platform;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;

import java.io.Serializable;

public class AddPlayerDTO implements Serializable {
    String club_name;
    Club club;

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
}
