package sample.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;
import sample.Action_club_country_player.Action;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Country;
import sample.Action_club_country_player.Player;
import sample.MyListener;
import sample.util.LogOutDTO;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewPage implements Initializable {
    public Main main;
    private  Club club=new Club();
    private  ArrayList<Player> Selected_Players=new ArrayList<>();
    private ArrayList<Country> countries=new ArrayList<>();
    private  ArrayList<Club> clubs;
    int selected_player_count=0;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private ImageView player_img;
    @FXML
    private javafx.scene.layout.BorderPane BorderPane;
    @FXML
    private GridPane grid;
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
        animation.startingAnimation(this);
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
                for(Club club:clubs)
                {
                    for(Player player:club.getPlayers())
                    {
                        if(player.getName().equalsIgnoreCase(name))
                        {
                            players.add(player);
                        }
                    }
                }
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
        else if(search_field.getPromptText().equalsIgnoreCase("Enter the country name"))
        {
            String[] Country=search_field.getText().split(",");
            ArrayList <Player> players=new ArrayList<>();
            for(String country:Country)
            {
                    for(Club club:clubs)
                    {
                        for(Player player:club.getPlayers())
                        {
                            if(country.equalsIgnoreCase(player.getCountry()))
                            {
                                players.add(player);
                            }
                        }
                    }


            }
            if(!players.isEmpty())
            {
                grid.getChildren().clear();
                grid_pane_action(players);
            }
            else
            {
                snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Country Not Found")));
            }

        }
        else if(search_field.getPromptText().equalsIgnoreCase("Enter the club name"))
        {

            String[] Club_name=search_field.getText().split(",");
            ArrayList <Player> players=new ArrayList<>();
            for(String clubName:Club_name)
            {
                for(Club club: clubs)
                {
                    if(club.getName().equalsIgnoreCase(clubName))
                    {
                      players.addAll(club.getPlayers());
                      break;
                    }
                }
            }

            if(!players.isEmpty())
            {
                grid.getChildren().clear();
                grid_pane_action(players);
            }
            else
            {
                snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Club Not Found")));
            }
        }
        else if(search_field.getPromptText().equalsIgnoreCase("Enter the club name for Max Age"))
        {

            String[] Club_name=search_field.getText().split(",");
            ArrayList <Player> players=new ArrayList<>();
            for(String club_name:Club_name)
            {
                for(Club club: clubs)
                {
                    if(club.getName().equalsIgnoreCase(club_name))
                    {
                        players.addAll(Action.Players_With_Maximum_Age(club));
                    }
                }
            }
            search_field.setText("Player Found: "+players.size());
            if(!players.isEmpty())
            {
                grid.getChildren().clear();
                grid_pane_action(players);
            }


        }

        else if (search_field.getPromptText().equalsIgnoreCase("Enter The Club Name for Max Salary"))
        {

            String[] Club_name=search_field.getText().split(",");
            ArrayList <Player> players=new ArrayList<>();
            for(String club_name:Club_name)
            {
                for(Club club: clubs)
                {
                    if(club.getName().equalsIgnoreCase(club_name))
                    {
                        players.addAll(Action.Players_With_Maximum_Salary(club,club.getName()));
                    }
                }
            }
            search_field.setText("Player Found: "+players.size());
            if(!players.isEmpty())
            {
                grid.getChildren().clear();
                grid_pane_action(players);
            }




        }
        else if(search_field.getPromptText().equalsIgnoreCase("Enter The Club Name for Max Height"))
        {
            String[] Club_name=search_field.getText().split(",");
            ArrayList <Player> players=new ArrayList<>();
            for(String club_name:Club_name)
            {
                for(Club club: clubs)
                {
                    if(club.getName().equalsIgnoreCase(club_name))
                    {
                        players.addAll(Action.Players_With_Maximum_Height(club,club.getName()));
                    }
                }
            }
            search_field.setText("Player Found: "+players.size());
            if(!players.isEmpty())
            {
                grid.getChildren().clear();
                grid_pane_action(players);
            }
        }
        else if(search_field.getPromptText().equalsIgnoreCase("Enter The Position"))
        {

            String[] Str=search_field.getText().split(",");
            ArrayList<Player> temp=new ArrayList<>();
            for(String str:Str)
            {
                for(Club club:clubs)
                {
                    temp.addAll(Action.Search_With_Position(club,str));
                }
            }

            if(!temp.isEmpty())
            {
                    grid.getChildren().clear();
                    grid_pane_action(temp);
            }
            else {
                snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Position Not Found")));
            }

        }
        else if(search_field.getPromptText().equalsIgnoreCase("Enter The Salary Range(Min-Max)"))
        {

            String[] str=search_field.getText().split("-",2);
            grid.getChildren().clear();
            ArrayList<Player> players=new ArrayList<>();
            for(Club club:clubs)
            {
                players.addAll(Action.Search_With_Salary(club,str));
            }

            selected_player_count=0;
            if(!players.isEmpty())
                grid_pane_action(players);

            else
            {

                snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Player Not Found")));
            }

        }
        else if(search_field.getPromptText().equalsIgnoreCase("Enter The Club Name for Total Salary"))
        {
            String str=search_field.getText();
            snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("You have not log in.")));
        }
        else
        {
            snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("Please Press a Search Button")));
        }


    }

    public  void print(Player player) {
        if(player!=null)
        {

            search_field.clear();
            search_field.setPromptText("Search Something");
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../FXML/Player_Details.fxml"));
            try {
                Parent root=loader.load();
                player_details controller=loader.getController();
                controller.setMain(this);
                controller.setplayer(player);
                stage.setTitle("Player Details");
                Scene scene=new Scene(root);
                scene.getStylesheets().add("sample/CSS/ViewPage.css");
                stage.show();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void grid_set_up() {
     ArrayList<Player> players=new ArrayList<>();
     for(Club club:clubs)
     {
         players.addAll(club.getPlayers());
     }
        if(players.size()>0)
        {
            myListener=new MyListener() {
                @Override
                public void onClickListener(Player player) {
                    search_field.clear();
                    search_field.setPromptText("Enter Something");
                    print(player);

                }
            };
        }
        grid_pane_action(players);
    }

    public void by_name_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter the player's name:");
    }

    public void by_club_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter the club name");
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
    }

    public void by_max_height_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter The Club Name for Max Height");
    }

    public void by_max_salary_action(ActionEvent actionEvent) {
        search_field.clear();
        search_field.setPromptText("Enter The Club Name for Max Salary");
    }

    public void by_countrywise_action(ActionEvent actionEvent) {
        snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("You have not log in.")));

    }

    public void by_total_salary_action(ActionEvent actionEvent) {
        snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("You have not log in.")));
    }

    public void all_players_action(ActionEvent actionEvent) {
        ArrayList<Player> players=new ArrayList<>();
        for(Club club:clubs)
        {
            players.addAll(club.getPlayers());
        }
        grid.getChildren().clear();
        grid_pane_action(players);
    }



    public void setClubs(ArrayList<Club> clubs) {
        club_name.setText("VIEWER");
        this.clubs=clubs;
        grid_set_up();
    }

    public void home_button_action(ActionEvent actionEvent) {
        ArrayList<Player> players=new ArrayList<>();
        for(Club club:clubs)
        {
            players.addAll(club.getPlayers());
        }
        grid.getChildren().clear();
        grid_pane_action(players);
        search_field.clear();
        search_field.setPromptText("Search Something");

    }

    public void market_button_action(ActionEvent actionEvent) {
        snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout("You have not log in.")));
    }

    public void logIn_button_action(ActionEvent actionEvent) {
        LogOutDTO logOutDTO=new LogOutDTO();
        logOutDTO.setClub_name(club_name.getText());
        try {
            main.getNetworkUtil().write(logOutDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Node getRootNode() {
        return BorderPane;
    }
}
