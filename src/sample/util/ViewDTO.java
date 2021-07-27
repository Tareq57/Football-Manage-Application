package sample.util;

import sample.Action_club_country_player.Club;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewDTO implements Serializable {
    ArrayList<Club> clubs;

    public ArrayList<Club> getClubs() {
        return clubs;
    }

    public void setClubs(ArrayList<Club> clubs) {
        this.clubs = clubs;
    }
}
