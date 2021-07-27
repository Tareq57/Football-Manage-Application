package sample.server;

import sample.Action_club_country_player.Player;
import sample.util.NetworkUtil;

public class ClientInfo {
    private  String club_name,file_name;
    NetworkUtil networkUtil;
    Player player;
    boolean status=false;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public NetworkUtil getNetworkUtil() {
        System.out.println(networkUtil);
        return networkUtil;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
