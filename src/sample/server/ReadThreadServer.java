package sample.server;

import javafx.application.Platform;
import sample.Action_club_country_player.Action;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;
import sample.util.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final NetworkUtil networkUtil;
    public HashMap<String, String> userMap;
    public ArrayList<Club> clubs;
    public  ArrayList<Player> players;
    public HashMap<String, ClientInfo> clientMap;
    int cnt=0;


    public ReadThreadServer(HashMap<String, String> map, ArrayList<Club> clubs, ArrayList<Player> players, NetworkUtil networkUtil, HashMap<String, ClientInfo> clientMap) {
        this.userMap = map;
        this.networkUtil = networkUtil;
        this.clubs=clubs;
        this.players=players;
        this.clientMap=clientMap;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        String password = userMap.get(loginDTO.getUserName());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));
                        Iterator<String> iterator = clientMap.keySet().iterator();
                        boolean ok=true;
                        while (iterator.hasNext())
                        {
                            String name=iterator.next();
                            if(name.equalsIgnoreCase(loginDTO.getUserName())&&!clientMap.get(name).getFile_name().equalsIgnoreCase("log in page"))
                            {
                                loginDTO.setStatus(!name.equalsIgnoreCase(loginDTO.getUserName()));
                                String[] strings={"Invalid Log in","Duplicate Log in","This club is already running in another window"};
                                networkUtil.write(strings);
                                ok=false;
                                break;
                            }
                        }
                        if(ok)
                        {
                            if(loginDTO.isStatus())
                            {
                                ClientInfo clientInfo=new ClientInfo();
                                clientInfo.setClub_name(loginDTO.getUserName().toUpperCase());
                                clientInfo.setFile_name("Club Page");
                                clientInfo.setNetworkUtil(networkUtil);
                                System.out.println(clientInfo.getNetworkUtil());
                                clientMap.put(loginDTO.getUserName().toUpperCase(),clientInfo);
//                                System.out.println(clientMap.size()+" 69 line in read thread");
                            }
                            for(Club club:clubs)
                            {
                                if(club.getName().equalsIgnoreCase(loginDTO.getUserName()))
                                {
                                    loginDTO.setPlayers(club.getPlayers());
                                    break;
                                }
                            }
                            networkUtil.write(loginDTO);
                        }

                    }
                    ///SELL DTO
                    else if(o instanceof SellReqDTO) {
                        SellReqDTO sellReqDTO = (SellReqDTO) o;
                        Player player = sellReqDTO.getPlayer();
                        Club club = sellReqDTO.getClub();
                        for (Club club1 : clubs) {
                            if (club.getName().equalsIgnoreCase(club1.getName())) {
                                for (Player player1 : club1.getPlayers()) {
                                    if (player.getName().equalsIgnoreCase(player1.getName())) {
                                        player1.setSold(false);
                                        club1.removePlayer(player1);
                                        club = club1;
                                        break;
                                    }
                                }
                            }
                        }
                        Databse db=new Databse();
                        db.add(player,"UnSoldPlayer");
                        db.delete(player,"player_database");
                        players.add(player);
                        sellReqDTO.setClub(club);
                        networkUtil.write(sellReqDTO);
                        Market();

                    }
                    else if(o instanceof MarketDTO) {
                        MarketDTO marketDTO=(MarketDTO) o;
                        String club_name=marketDTO.getClub_name().toUpperCase();
                        ArrayList<Player> players1=new ArrayList<>();
                        ClientInfo clientInfo=new ClientInfo();
                        clientInfo.setClub_name(club_name);
                        clientInfo.setFile_name("Market Page");
                        clientInfo.setNetworkUtil(networkUtil);
                        clientMap.put(club_name,clientInfo);
                        for(Player player:players)
                        {
                            if(player.getClub().equalsIgnoreCase(club_name))
                                continue;
                            players1.add(player);

                        }
                        marketDTO.setPlayerArrayList(players1);
                        networkUtil.write(marketDTO);

                    }
                    //BUY DTO
                    else if(o instanceof BuyReqDTO)
                    {
                        BuyReqDTO buyReqDTO=(BuyReqDTO) o;
                        String club_name=buyReqDTO.getClub_name().toUpperCase();
                        Player player=buyReqDTO.getPlayer();
                        boolean ok=true;
                        int temp=cnt;
                        for(Player player1:players)
                        {
                            if(player1.getName().equalsIgnoreCase(player.getName()))
                            {
                                player1.setSold(true);
                                players.remove(player1);
                                cnt++;
                                break;
                            }
                        }
                        if(temp<cnt)
                        {
                            for(Club club:clubs)
                            {
                                if(club.getName().equalsIgnoreCase( club_name))
                                {
                                    player.setClub(club_name);

                                    player.setSold(true);
                                    club.addPlayer(player);
                                    Databse databse=new Databse();
                                    databse.add(player,"player_database");
                                    databse.delete(player,"UnSoldPlayer");
                                    break;
                                }
                            }

                        }
                        else {

                                    String[] str={"Invalid Request","Buy Problem for "+club_name,"This player is already sold.Please go back to you market page"};
                                    networkUtil.write(str);
                                    player.setSold(true);


                        }
                        Market();

                    }
                    else if(o instanceof  LogOutDTO)
                    {
                        LogOutDTO logOutDTO=(LogOutDTO) o;
                        ClientInfo clientInfo=new ClientInfo();
                        clientInfo.setClub_name(logOutDTO.getClub_name());
                        clientInfo.setFile_name("Log in page");
                        clientInfo.setNetworkUtil(networkUtil);
                        clientMap.put(logOutDTO.getClub_name(),clientInfo);
                        networkUtil.write(logOutDTO);
                    }
                    else  if(o instanceof  HomeDTO)
                    {
                        HomeDTO homeDTO=(HomeDTO) o;
                        String club_name= homeDTO.getClub_name();
                        ClientInfo clientInfo=new ClientInfo();
                        clientInfo.setClub_name(club_name);
                        clientInfo.setFile_name("Club Page");
                        clientInfo.setNetworkUtil(networkUtil);
                        clientMap.put(club_name,clientInfo);
                        for(Club club:clubs)
                        {
                            if(club.getName().equalsIgnoreCase(homeDTO.getClub_name()))
                            {
                                homeDTO.setPlayers(club.getPlayers());
                                break;
                            }
                        }
                        networkUtil.write(homeDTO);
                    }
                    else if(o instanceof ViewDTO)
                    {
                        ViewDTO viewDTO=(ViewDTO) o;
                        viewDTO.setClubs(clubs);
                        networkUtil.write(viewDTO);
                    }
                    else if(o instanceof  Player_DetailsDTO)
                    {

                        Player_DetailsDTO player_detailsDTO=(Player_DetailsDTO) o;
                        for(Club club:clubs)
                        {
                            if(club.getName().equalsIgnoreCase(player_detailsDTO.getPlayer().getClub()))
                            {
                                player_detailsDTO.setClub(club);
                                break;
                            }
                        }
                        if(player_detailsDTO.getStatus().equalsIgnoreCase("SELL"))
                        {

                            networkUtil.write(player_detailsDTO);
                        }
                        else
                        {
                            networkUtil.write(player_detailsDTO);
                        }
                    }
                    else if(o instanceof  AddPlayerDTO)
                    {
                        AddPlayerDTO addPlayerDTO=(AddPlayerDTO) o ;
                        networkUtil.write(addPlayerDTO);
                    }
                    else if(o instanceof AddPlayerDTO2)
                    {
                        AddPlayerDTO2 addPlayerDTO2=(AddPlayerDTO2) o;
                        boolean ok=true;
                        for(Club club:clubs)
                        {
                            for(Player player:club.getPlayers())
                            {
                                if(player.getName().equalsIgnoreCase(addPlayerDTO2.getPlayer().getName()))
                                {
                                    String[] str={"Invalid Player","Duplication Player","There exits same player in databse"};
                                    networkUtil.write(str);
                                    ok=false;
                                    break;
                                }
                            }
                        }
                        if(ok)
                        {
                            for(Club club:clubs)
                            {
                                if(club.getName().equalsIgnoreCase(addPlayerDTO2.getPlayer().getClub()))
                                {
                                    Databse databse=new Databse();
                                    databse.add(addPlayerDTO2.getPlayer(),"player_database");
                                    club.addPlayer(addPlayerDTO2.getPlayer());
                                    AddPlayerDTO addPlayerDTO=new AddPlayerDTO();
                                    addPlayerDTO.setClub(club);
                                    addPlayerDTO.setClub_name(club.getName());
                                    networkUtil.write(addPlayerDTO);
                                    HomeDTO homeDTO=new HomeDTO();
                                    homeDTO.setPlayers(club.getPlayers());
                                    homeDTO.setClub_name(club.getName());
                                    networkUtil.write(homeDTO);
                                    break;
                                }
                            }
                        }
                    }
                    else if(o instanceof  EditDTO)
                    {
                        EditDTO editDTO=(EditDTO)  o;
                        for(Club club:clubs)
                        {
                            if(club.getName().equalsIgnoreCase(editDTO.getClub().getName()))
                            {
                                for(Player player:club.getPlayers())
                                {
                                    if(player.getName().equalsIgnoreCase(editDTO.getPlayer().getName()))
                                    {
                                        Databse databse=new Databse();
                                        databse.delete(player,"player_database");
                                        databse.add(editDTO.getPlayer(),"player_database");
                                        player=editDTO.getPlayer();
                                        Player_DetailsDTO player_detailsDTO=new Player_DetailsDTO();
                                        player_detailsDTO.setPlayer(player);
                                        player_detailsDTO.setClub_name(club.getName());
                                        player_detailsDTO.setClub(club);
                                        player_detailsDTO.setStatus("SELL");
                                        networkUtil.write(player_detailsDTO);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else if(o instanceof  StageCloseDTO)
                    {
                        StageCloseDTO stageCloseDTO=(StageCloseDTO) o;
                        clientMap.remove(stageCloseDTO.getClub_name());
//                        System.out.println(clientMap.size()+"Number 296 read thread server");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void Market() {
        try {
            Iterator<String> iterator = clientMap.keySet().iterator();
            while (iterator.hasNext())
            {
                String clubName=iterator.next().toUpperCase();
                if(clientMap.get(clubName).getFile_name().equalsIgnoreCase("Market page"))
                {
                    MarketDTO marketDTO=new MarketDTO();
                    marketDTO.setClub_name(clubName);
                    ArrayList<Player> playerArrayList=new ArrayList<>();
                    for(Player player1:players)
                    {
                        if(player1.getClub().equalsIgnoreCase(clubName))
                        {
                            continue;
                        }
                        playerArrayList.add(player1);
                    }
                    marketDTO.setPlayerArrayList(playerArrayList);
                    clientMap.get(clubName).getNetworkUtil().write(marketDTO);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}



