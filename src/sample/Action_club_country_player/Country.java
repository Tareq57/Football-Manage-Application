package sample.Action_club_country_player;

import java.io.Serializable;
import java.util.ArrayList;

public class Country  implements Serializable {
    private ArrayList<Player> players = new ArrayList<>();
    private String name;

    public Country() {
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
