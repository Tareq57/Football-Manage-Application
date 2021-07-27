package sample.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Action_club_country_player.Action;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Country;
import sample.Action_club_country_player.Player;
import sample.MyListener;
import sample.util.*;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class ClubPage implements Initializable {
    public Main main;
    public   Club club=new Club();
    private  ArrayList<Player> Selected_Players=new ArrayList<>();
    private ArrayList<Country> countries=new ArrayList<>();
    private Player player_detail;
    private int selected_player_count=0;
    public  Stage stage1=new Stage();
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
    private JFXTextArea Search_Country,Search_Club,Search_Name,Search_Age,Search_Position,Search_Number,Search_Weeklysalary;
    @FXML
    private JFXTextField search_field;
    @FXML
    private Label club_name;
    private MyListener myListener;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String css=this.getClass().getClassLoader().getResource("sample/CSS/ViewPage.css").toExternalForm();
        snackbar.setPrefWidth(927);
        snackbar.getStylesheets().add(css);
        animation.startingAnimationClubPage(this);
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
    public void search_field_onAction(ActionEvent actionEvent) {
        if(search_field.getPromptText().equalsIgnoreCase("Enter the player's name:"))
        {
            String[] Name=search_field.getText().split(",");
            ArrayList<Player> players=new ArrayList<>();
            for(String name:Name)
            {
                Player player=Action.Search_With_PlayerName(club,name);
                if(player!=null)
                    players.add(player);
            }
            if(!players.isEmpty())
            {
                grid.getChildren().clear();
                grid_pane_action(players);
            }
            else
            {
                snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Player Not Found")));
            }

        }
        else if(search_field.getPromptText().equalsIgnoreCase("Enter the country name")) {
            String[] Country=search_field.getText().split(",");
            ArrayList <Player> temp=new ArrayList<>();
            for(String country:Country)
            {

                for(Player player:club.getPlayers())
                {
                    if(country.equalsIgnoreCase(player.getCountry()))
                    {
                        temp.add(player);
                    }
                }


            }
            if(!temp.isEmpty())
            {
                System.out.println("Every thing done");
                grid.getChildren().clear();
                grid_pane_action(temp);
            }
            else
            {
                snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Country Not Found")));
            }

        }

        else if(search_field.getPromptText().equalsIgnoreCase("Enter The Position")) {
            Player player=null;
            String[] Str=search_field.getText().split(",");
            ArrayList<Player> temp=new ArrayList<>();
            for(String str:Str)
            {
                Selected_Players=Action.Search_With_Position(club,str);
                for(Player player1:Selected_Players)
                    temp.add(player1);
            }
            selected_player_count=0;
            if(!temp.isEmpty())
            {
                grid.getChildren().clear();
                grid_pane_action(temp);
            }
            else {
                snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Position Not Found")));
            }

        }
        else if(search_field.getPromptText().equalsIgnoreCase("Enter The Salary Range(Min-Max)")) {

            String[] str=search_field.getText().split("-",2);
            grid.getChildren().clear();
            Selected_Players=Action.Search_With_Salary(club,str);
            selected_player_count=0;
            if(!Selected_Players.isEmpty())
            {


                grid_pane_action(Selected_Players);

            }
            else {

                snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Player Not Found")));
            }

        }
        else {
            snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Please Press a Search Button")));
        }
    }

    public  void print(Player player) {
        if(player!=null)
        {

            search_field.clear();
            search_field.setPromptText("Search Something");
            Player_DetailsDTO player_detailsDTO=new Player_DetailsDTO();
            player_detailsDTO.setStatus("SELL");
            player_detailsDTO.setClub(club);
            player_detailsDTO.setPlayer(player);
            try {
                main.getNetworkUtil().write(player_detailsDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void grid_set_up() {

        ArrayList<Player> players=club.getPlayers();
        if(players.size()>0)
        {
            myListener=new MyListener() {
                @Override
                public void onClickListener(Player player) {
                    player_detail=player;
                    search_field.clear();
                    search_field.setPromptText("Enter Something");
                    print(player_detail);


                }
            };
        }
        grid_pane_action(players);
    }

    public void by_name_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter the player's name:");
    }
    public void by_country_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter the country name");
    }

    public void by_position_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter The Position");
    }

    public void by_salary_range_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter The Salary Range(Min-Max)");
    }

    public void by_max_age_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter the club name for Max Age");
        Selected_Players=Action.Players_With_Maximum_Age(club);
        search_field.setText("Player Found: "+Selected_Players.size());
        if(Selected_Players.size()>0)
        {
            grid.getChildren().clear();
            grid_pane_action(Selected_Players);
        }
        return;
    }

    public void by_max_height_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter The Club Name for Max Height");
        Selected_Players=Action.Players_With_Maximum_Height(club,club.getName());
        search_field.setText("Player Found: "+Selected_Players.size());
        if(!Selected_Players.isEmpty())
        {
            if(Selected_Players.size()>0)
            {
                grid.getChildren().clear();
                grid_pane_action(Selected_Players);
            }
        }
    }

    public void by_max_salary_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter The Club Name for Max Salary");
        Selected_Players=Action.Players_With_Maximum_Salary(club,club.getName());
        search_field.setText("Player Found: "+Selected_Players.size());
        selected_player_count=0;
        if(!Selected_Players.isEmpty())
        {
            if(Selected_Players.size()>0)
            {
                grid.getChildren().clear();
                grid_pane_action(Selected_Players);
            }
        }
    }

    public void by_countrywise_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter Something");
        ArrayList<Country> countryArrayList=Action.Search_With_Country(club);
        ArrayList<Player> players=new ArrayList<>();
        VBox vBox=new VBox();
        vBox.setMinHeight(300);
        vBox.setMinWidth(400);
        for(Country country:countryArrayList)
        {
            Label label=new Label();
            label.setText(country.getName()+"  :  "+country.getPlayers().size());
            label.setStyle(" -fx-text-fill: white;-fx-text-alignment: center;-fx-background-width: 400;-fx-background-height: 50;-fx-font-family: Algerian;-fx-font-size: 30 ");
            vBox.getChildren().add(label);
            for(Player player:country.getPlayers())
            {
                players.add(player);
            }
        }
        vBox.setStyle("-fx-background-color:\n" +
                "            radial-gradient(center 40% -50%,radius 10%,indianred 50%,red 50%),\n" +
                "            linear-gradient(rgba(2, 21, 42, 0.75), #010c01,#02152ABE);");
        Scene scene=new Scene(vBox);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
        grid.getChildren().clear();
        grid_pane_action(players);


    }

    public void by_total_salary_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter The Club Name for Total Salary");
        search_field.clear();
        double total_salary=Action.Yearly_Salary(club,club.getName());
        DecimalFormat res=new DecimalFormat("0");
        res.setMaximumFractionDigits(2);
        VBox vBox=new VBox();
        vBox.setMinHeight(250);
        vBox.setMinWidth(400);
        vBox.setStyle("-fx-background-color:\n" +
                "            radial-gradient(center 40% -50%,radius 10%,indianred 50%,red 50%),\n" +
                "            linear-gradient(rgba(2, 21, 42, 0.75), #010c01,#02152ABE);");
        Label label=new Label();
        label.setText("The Total Yearly Salary Of ");
        Label label1=new Label();
        label1.setText(club_name.getText()+" Club is "+res.format(total_salary)+" $");
        label.setStyle(" -fx-text-fill: white;-fx-text-alignment: center;-fx-background-width: 400;-fx-background-height: 50;-fx-font-family: Algerian;-fx-font-size: 30 ");
        label1.setStyle(" -fx-text-fill: white;-fx-text-alignment: center;-fx-background-width: 400;-fx-background-height: 50;-fx-font-family: Algerian;-fx-font-size: 30 ");
        vBox.getChildren().add(label);
        vBox.getChildren().add(label1);
        Scene scene=new Scene(vBox);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void all_players_action(ActionEvent actionEvent) {
        ArrayList<Player> players=club.getPlayers();
        grid.getChildren().clear();
        grid_pane_action(players);
    }

    public void setName(Club club) {
        this.club=club;
        club_name.setText(club.getName());
        grid_set_up();

    }


    public void Market_OnAction(ActionEvent actionEvent) {
        MarketDTO marketDTO=new MarketDTO();
        marketDTO.setClub_name(club_name.getText().toLowerCase());
        try {
            main.getNetworkUtil().write(marketDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout_action(ActionEvent actionEvent) {
        LogOutDTO logOutDTO=new LogOutDTO();
        logOutDTO.setClub_name(club_name.getText());
        try {
            main.getNetworkUtil().write(logOutDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Node getRootNode()
    {
        return BorderPane;
    }

    public void home_onAction(ActionEvent actionEvent) {
        HomeDTO homeDTO=new HomeDTO();
        homeDTO.setClub_name(club_name.getText());
        try {
            main.getNetworkUtil().write(homeDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add_player_action(ActionEvent actionEvent) {
        AddPlayerDTO addPlayerDTO=new AddPlayerDTO();
        addPlayerDTO.setClub(club);
        addPlayerDTO.setClub_name(club_name.getText());
        try {
            main.getNetworkUtil().write(addPlayerDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
