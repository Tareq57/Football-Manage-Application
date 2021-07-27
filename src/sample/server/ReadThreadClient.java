package sample.server;

import javafx.application.Platform;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;
import sample.controller.Main;
import sample.util.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class ReadThreadClient implements Runnable {
    private final Thread thr;
    private final Main main;
    public  Club club;
    public ReadThreadClient(Main main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {

        try {
            while (true) {
                Object o = main.getNetworkUtil().read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginDTO.isStatus()) {
                                    try {
                                        club=new Club();
                                        club.setName(loginDTO.getUserName().toUpperCase());
                                        club.setPlayers(loginDTO.getPlayers());
                                        main.showClubPage(club);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    main.showAlert("Invalid Credentials","Incorrect Credentials","The username and password you provided is not correct.");
                                }

                            }
                        });
                    }
                    if(o instanceof SellReqDTO)
                    {
                        SellReqDTO sellReqDTO=(SellReqDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showClubPage(sellReqDTO.getClub());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    if(o instanceof MarketDTO)
                    {
                        MarketDTO marketDTO=(MarketDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    main.showMarketPage(marketDTO.getPlayerArrayList(),marketDTO.getClub_name().toUpperCase());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                     }
                    if(o instanceof BuyReqDTO)
                    {
                        BuyReqDTO buyReqDTO=(BuyReqDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showMarketPage(buyReqDTO.getPlayerArrayList(),buyReqDTO.getClub_name().toUpperCase());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    if(o instanceof LogOutDTO)
                    {
                        LogOutDTO logOutDTO=(LogOutDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showLoginPage();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    if(o instanceof  HomeDTO)
                    {
                        HomeDTO homeDTO=(HomeDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                club=new Club();
                                club.setName(homeDTO.getClub_name().toUpperCase());
                                club.setPlayers(homeDTO.getPlayers());
                                try {
                                    main.showClubPage(club);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    if(o instanceof  ViewDTO)
                    {
                        ViewDTO viewDTO=(ViewDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showViewerPage(viewDTO.getClubs());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                    if(o instanceof  Player_DetailsDTO)
                    {
                        Player_DetailsDTO player_detailsDTO=(Player_DetailsDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showPlayerDetails(player_detailsDTO.getPlayer(),player_detailsDTO.getStatus(),player_detailsDTO.getClub(),player_detailsDTO.getClub_name());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    if(o instanceof  String[])
                    {
                        String[] strings=(String[]) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                main.showAlert(strings[0],strings[1],strings[2]);
                            }
                        });
                    }
                    if(o instanceof  AddPlayerDTO)
                    {
                        AddPlayerDTO addPlayerDTO=(AddPlayerDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showAddPlayer(addPlayerDTO.getClub_name(),addPlayerDTO.getClub());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                main.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



