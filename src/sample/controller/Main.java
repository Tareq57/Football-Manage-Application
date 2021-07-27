package sample.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;
import sample.server.ReadThreadClient;
import sample.util.NetworkUtil;
import sample.util.StageCloseDTO;


import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    private Stage stage,stage1;
    Stage stage2=new Stage();
    private static NetworkUtil networkUtil;

    public Main getmain() {
        return this;
    }

    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setMaxHeight(650);
        stage.setMaxWidth(780);
        stage1=new Stage();
        connectToServer();
        showLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 55555;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThreadClient(this);
    }


    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/sample.fxml"));
        Parent root = loader.load();

        // Loading the controller
        Controller controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Login");
        Scene scene=new Scene(root);
        scene.getStylesheets().add("sample/CSS/sample.css");
        stage.setScene(scene);
        stage.show();
    }
    public void showViewerPage(ArrayList<Club> clubs) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/ViewPage.fxml"));
        Parent root= loader.load();
        ViewPage controller=loader.getController();
        controller.setMain(this);
        controller.setClubs(clubs);
        stage.setTitle("ViewPage");
        Scene scene=new Scene(root);
        scene.getStylesheets().add("sample/CSS/ViewPage.css");
        stage.setScene(scene);
        stage.show();
    }
    public void showPlayerDetails(Player player,String str,Club club,String market_club) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/Player_Details.fxml"));
        try {
            Parent root=loader.load();
            player_details controller=loader.getController();
            controller.init(this,stage1,str,club,market_club);
            controller.setplayer(player);
            stage1.setTitle("Player Details");
            Scene scene=new Scene(root);
            scene.getStylesheets().add("sample/CSS/ViewPage.css");
            stage1.show();
            stage1.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showClubPage(Club club) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/ClubPage.fxml"));
        Parent root= loader.load();
        ClubPage controller=loader.getController();
        controller.setMain(this);
        controller.setName(club);
        stage.setTitle("ClubPage");
        Scene scene=new Scene(root);
        scene.getStylesheets().add("sample/CSS/ViewPage.css");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                StageCloseDTO stageCloseDTO=new StageCloseDTO();
                stageCloseDTO.setClub_name(club.getName());
                try {
                    networkUtil.write(stageCloseDTO);
                    Platform.exit();
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        stage.show();
    }

    public void showMarketPage(ArrayList<Player> playerArrayList, String club_name) throws IOException {

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/MarketPage.fxml"));
        Parent root= loader.load();
        MarketPage controller=loader.getController();
        controller.setMain(this);
        controller.setPlayer(playerArrayList,club_name);
        stage.setTitle("MarketPage");
        Scene scene=new Scene(root);
        scene.getStylesheets().add("sample/CSS/ViewPage.css");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                StageCloseDTO stageCloseDTO=new StageCloseDTO();
                stageCloseDTO.setClub_name(club_name);
                try {
                    networkUtil.write(stageCloseDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();
    }
    public void showAlert(String title,String header_text,String content_text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header_text);
        alert.setContentText(content_text);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void showAddPlayer(String club_name, Club club) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/AddPlayer.fxml"));
        Parent root= loader.load();
        AddPlayer controller=loader.getController();
        controller.init(this,club_name,club);

        stage2.setTitle("ClubPage");
        Scene scene=new Scene(root);
        scene.getStylesheets().add("sample/CSS/ViewPage.css");
        stage2.setScene(scene);
        stage2.show();
    }
}
