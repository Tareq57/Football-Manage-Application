package sample.Action_club_country_player;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Action {



    public static ArrayList<Club> Add_in_Club(ArrayList<Player> arrayList) {
        ArrayList<Club> clubs = new ArrayList<>();
        for (Player player : arrayList) {
            boolean ok = true;
            for (Club club : clubs) {
                if (club.getName().equalsIgnoreCase(player.getClub())) {
                    club.addPlayer(player);
                    ok = false;
                    break;
                }

            }
            if (ok) {
                Club club = new Club();
                club.addPlayer(player);
                club.setName(player.getClub());
                clubs.add(club);
            }
        }
        return clubs;

    }
    public static ArrayList<Player> AllPlayers(ArrayList<Club>clubs) {
        ArrayList<Player> players=new ArrayList<>();
        for(Club club:clubs)
        {
            for(Player player:club.getPlayers())
            {
                players.add(player);
            }

        }
        return players;
    }
    public static void DatabaseWrite(ArrayList<Club> clubs,String fileName,ArrayList<Player> players) {
        try (FileWriter fw = new FileWriter(fileName)) {
            BufferedWriter bw = new BufferedWriter(fw);
            if(fileName.equalsIgnoreCase("players.txt"))
            {
                for (Club club : clubs) {
                    for (Player player : club.getPlayers()) {
                        fw.write(player.getName() + "," + player.getCountry() + "," + player.getAge() + "," + player.getHeight() + "," + player.getClub() + "," + player.getPosition() + "," + player.getNumber() + "," + player.getWeeklySalary() + ","+player.getImg()+"\r\n");
                    }
                }
                fw.close();
                bw.close();
            }
            else
            {
                for(Player player:players)
                {
                    fw.write(player.getName() + "," + player.getCountry() + "," + player.getAge() + "," + player.getHeight() + "," + player.getClub() + "," + player.getPosition() + "," + player.getNumber() + "," + player.getWeeklySalary() +","+player.getImg()+ "\r\n");
                }

                fw.close();
                bw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static double Yearly_Salary(Club club,String str) {
        double total_salary=0;
            if (club.getName().equalsIgnoreCase(str)) {
                total_salary = club.getYearlySalary();
                return total_salary;
            }

        return total_salary;
    }
    public static ArrayList<Player> Players_With_Maximum_Height(Club club,String str) {
        ArrayList<Player> result=new ArrayList<>();

            if (club.getName().equalsIgnoreCase(str)) {

                ArrayList<Player> players = club.getPlayers();
                double max_height = 0;
                for (Player player : players) {
                    if (max_height < player.getHeight()) {
                        max_height = player.getHeight();
                    }
                }

                for (Player player : players) {
                    if (player.getHeight() == max_height) {
                        result.add(player);
                    }
                }


            }

        return result;

    }
    public static ArrayList<Player> Players_With_Maximum_Age(Club club) {
                ArrayList<Player> players = club.getPlayers();
                int age = 0;
                for (Player player : players) {
                    if (age < player.getAge()) {
                        age = player.getAge();
                    }
                }
                ArrayList<Player> result=new ArrayList<>();
                for (Player player : players) {
                    if (player.getAge() == age) {
                        result.add(player);
                    }
                }
                return result;
            }
    public static ArrayList<Player> Players_With_Maximum_Salary(Club club,String str) {
        if(club.getName().equalsIgnoreCase(str))
        {
            ArrayList<Player> players = club.getPlayers();
            double max_salary = 0;
            for (Player player : players) {
                if (max_salary < player.getWeeklySalary()) {
                    max_salary = player.getWeeklySalary();
                }
            }
            ArrayList<Player> result=new ArrayList<>();
            for (Player player : players) {
                if (player.getWeeklySalary() == max_salary) {
                    result.add(player);
                }
            }
            return result;
        }
        return  null;


    }
    public static ArrayList<Country> Search_With_Country(Club club) {
        ArrayList<Country> countries = new ArrayList<>();


            for (Player player : club.getPlayers()) {
                boolean ok = true;
                for (Country country : countries) {
                    if (country.getName().equalsIgnoreCase(player.getCountry())) {
                        country.addPlayer(player);
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    Country country = new Country();
                    country.addPlayer(player);
                    country.setName(player.getCountry());
                    countries.add(country);
                }
            }

        return countries;

    }
    public static ArrayList<Player> Search_With_Salary(Club club,String[] str) {
        ArrayList<Player> result=new ArrayList<>();

            for (Player player : club.getPlayers()) {
                if (player.getWeeklySalary() >= Double.parseDouble(str[0]) && player.getWeeklySalary() <= Double.parseDouble(str[1])) {
                    result.add(player);
                }
            }

        return result;
    }
    public static ArrayList<Player> Search_With_Position(Club club,String str) {
        ArrayList<Player> result=new ArrayList<>();

            for (Player player : club.getPlayers()) {
                if (player.getPosition().equalsIgnoreCase(str)) {
                    result.add(player);


                }
            }


        return result;

    }
    public static Player Search_With_PlayerName(Club club,String str) {
        Player player1 =null;
            for (Player player : club.getPlayers()) {
                if (player.getName().equalsIgnoreCase(str)) {
                    return player;

                }
            }


        return  player1;
    }
    public static ArrayList<String> DatabaseRead(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (true) {
                String s = br.readLine();
                if (s == null) break;
                list.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
