package sample.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Action_club_country_player.Country;
import sample.Action_club_country_player.Player;
import sample.MyListener;
import sample.util.HomeDTO;
import sample.util.LogOutDTO;
import sample.util.MarketDTO;
import sample.util.Player_DetailsDTO;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MarketPage implements Initializable {
    public Main main;
    private  ArrayList<Player> Selected_Players;
    private ArrayList<Country> countries=new ArrayList<>();
    private Player player_detail;
    String clubName;
    private int selected_player_count=0;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private AnchorPane confirmation_anchorpane;
    @FXML
    private ImageView player_img;
    @FXML
    private javafx.scene.layout.BorderPane BorderPane;
    @FXML
    private GridPane grid;
    @FXML
    private Label club_name;
    private MyListener myListener;
    public   Stage stage1=new Stage();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String css=this.getClass().getClassLoader().getResource("sample/CSS/ViewPage.css").toExternalForm();
        snackbar.setPrefWidth(927);
        snackbar.getStylesheets().add(css);
    }
    public  void grid_pane_action(ArrayList<Player> players) {
        int column=0,row=1;
        try{
            for(Player player:players)
            {

                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("../FXML/grid_item_viewer.fxml"));
                AnchorPane anchorPane= loader.load();
                grid_item_controller controller= loader.getController();
                controller.setData(player,myListener);
                animation.scaleTransitioningStarting(anchorPane);
                grid.add(anchorPane,column++,row);
                if(column>2)
                {
                    column=0;
                    row++;

                }
                GridPane.setMargin(anchorPane,new Insets(10,10,10,10));


            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void setMain(Main main) {
        this.main = main;
    }


    public  void print(Player player) {
        if(player!=null)
        {

            Player_DetailsDTO player_detailsDTO=new Player_DetailsDTO();
            player_detailsDTO.setStatus("BUY");
            player_detailsDTO.setPlayer(player);
            player_detailsDTO.setClub_name(clubName);
            try {
                main.getNetworkUtil().write(player_detailsDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void grid_set_up() {
        ArrayList<Player>players=Selected_Players;
        if(players.size()>0)
        {
            myListener=new MyListener() {
                @Override
                public void onClickListener(Player player) {
                    player_detail=player;
                    print(player_detail);


                }
            };
        }
        grid_pane_action(players);
    }


    public void setPlayer(ArrayList<Player> playerArrayList, String club_name) {
        Selected_Players=playerArrayList;
        this.club_name.setText(club_name);
        clubName=club_name;
        grid_set_up();
    }

    public void all_players_action(ActionEvent actionEvent) {
        MarketDTO marketDTO=new MarketDTO();
        marketDTO.setClub_name(club_name.getText().toLowerCase());
        try {
            main.getNetworkUtil().write(marketDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout_onAction(ActionEvent actionEvent) {
        LogOutDTO logOutDTO=new LogOutDTO();
        logOutDTO.setClub_name(clubName);
        try {
            main.getNetworkUtil().write(logOutDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void home_onAction(ActionEvent actionEvent) {
        HomeDTO homeDTO=new HomeDTO();
        homeDTO.setClub_name(clubName);
        try {
            main.getNetworkUtil().write(homeDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Node getRootNode() {
        return  BorderPane;
    }
}
