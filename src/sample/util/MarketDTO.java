package sample.util;

import javafx.stage.Stage;
import sample.Action_club_country_player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class MarketDTO implements Serializable {
    String club_name;
    ArrayList<Player> playerArrayList;
    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
    }
}
