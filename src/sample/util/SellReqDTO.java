package sample.util;

import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;

import java.io.Serializable;

public class SellReqDTO  implements Serializable {
    private Player player;
    private Club club;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}