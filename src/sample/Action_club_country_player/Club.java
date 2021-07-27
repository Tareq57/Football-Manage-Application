package sample.Action_club_country_player;

import java.io.Serializable;
import java.util.ArrayList;

public class Club implements Serializable {
    private ArrayList<Player> players = new ArrayList<>();
    private String name;

    public Club() {
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

    public double getYearlySalary() {
        ArrayList<Double> players_yearly_salary = new ArrayList<>();
        for (Player player : players) {
            players_yearly_salary.add(player.getWeeklySalary() * 52.0);
        }
        double total_salary = 0.0;
        for (double d : players_yearly_salary) {
            total_salary += d;
        }
        return total_salary;
    }

    public void removePlayer(Player player1) {
        players.remove(player1);
    }
}
