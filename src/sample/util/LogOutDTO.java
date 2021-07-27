package sample.util;

import java.io.Serializable;

public class LogOutDTO implements Serializable {
    String club_name;

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }
}
