package sample.util;

import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;

import java.io.Serializable;

public class AddPlayerDTO2 implements Serializable {
    Player player;
    Club club;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
